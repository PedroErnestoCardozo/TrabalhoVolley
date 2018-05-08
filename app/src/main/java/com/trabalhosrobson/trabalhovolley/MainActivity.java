package com.trabalhosrobson.trabalhovolley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static String JSON_URL = "https://sistemagte.xyz/android/trabRobson/listar.php";
    ListView listView;
    List<UsuarioConst> usuarioList;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    ListViewAdapter adapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(MainActivity.this);

        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.barra_pesquisa);
        usuarioList = new ArrayList<>();
        PuxarDados();//metodo que traz o json do banco ja preenche o listview
        listView.setTextFilterEnabled(true);
        setupSearchView();//inicia o metodo de configurações da searchview
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);//passagem do contexto para usar o searchview
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Pesquisar...");
    }

    public void fab(View view) {
        Intent Tela = new Intent(this,CadastroUsuarios .class);
        startActivity(Tela);
    }

    private void PuxarDados() {
        progressDialog.setMessage("Carregando dados");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray jsonArray = obj.getJSONArray("nome");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                UsuarioConst usuarioConst = new UsuarioConst(jsonObject.getString("nome"),jsonObject.getString("sobrenome"),jsonObject.getString("email"),jsonObject.getString("foto"));
                                usuarioList.add(usuarioConst);
                            }
                            adapter = new ListViewAdapter(usuarioList, getApplicationContext());

                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onQueryTextChange(String newText){
        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
        } else {
            listView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        return false;
    }

}
