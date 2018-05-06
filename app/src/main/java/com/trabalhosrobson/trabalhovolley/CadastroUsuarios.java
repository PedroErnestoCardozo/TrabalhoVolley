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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CadastroUsuarios extends AppCompatActivity {

    Button btnFotos;
    private static int CODE_REQ = 0205;
    Bitmap photoBitmap;
    ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuarios);
        btnFotos = findViewById(R.id.btnImg);
        photoView = findViewById(R.id.imgSelecionada);

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

    }

}
