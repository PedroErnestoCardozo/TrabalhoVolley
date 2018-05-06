package com.trabalhosrobson.trabalhovolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CadastroUsuarios extends AppCompatActivity {

    Button btnFotos;
    private static int CODE_REQ = 0205;
    Bitmap photoBitmap;
    ImageView photoView;

    EditText nome,sobrenome,email;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "https://sistemagte.xyz/android/trabRobson/cad.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuarios);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(CadastroUsuarios.this);

        btnFotos = findViewById(R.id.btnImg);
        photoView = findViewById(R.id.imgSelecionada);
        nome = findViewById(R.id.nome);
        sobrenome = findViewById(R.id.sobrenome);
        email = findViewById(R.id.email);

        btnFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, CODE_REQ);

            }

        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == CODE_REQ) {
            Uri photoData = data.getData();

            try {
                photoBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),photoData);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            photoView.setImageBitmap(photoBitmap);
        }


    }


    public void cadastrarImg(View view) {
        progressDialog.setMessage("Cadastrando");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //Toast.makeText(cad_crianca.this, ServerResponse, Toast.LENGTH_LONG).show();
                        System.out.println(ServerResponse);
                        Toast.makeText(CadastroUsuarios.this, ServerResponse, Toast.LENGTH_LONG).show();
                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(CadastroUsuarios.this, "Cadastrado!", Toast.LENGTH_SHORT).show();
                        Intent tela = new Intent(CadastroUsuarios.this, MainActivity.class);
                        startActivity(tela);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        progressDialog.dismiss();

                        Toast.makeText(CadastroUsuarios.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("nome", nome.getText().toString());
                params.put("sobrenome", sobrenome.getText().toString());
                params.put("email", email.getText().toString());
                params.put("foto", imageToString(photoBitmap));

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private String imageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

}
