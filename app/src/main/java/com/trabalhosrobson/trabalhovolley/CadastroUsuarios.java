package com.trabalhosrobson.trabalhovolley;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CadastroUsuarios extends AppCompatActivity {

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    private static String URL_INSERT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuarios);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(CadastroUsuarios.this);
    }

    private void EnviarDados(){
        progressDialog.setMessage("Cadastrando");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();
                        Toast.makeText(CadastroUsuarios.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(CadastroUsuarios.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("id", "value");

                return params;
            }

        };

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
