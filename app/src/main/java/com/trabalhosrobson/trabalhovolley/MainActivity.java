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

    /**
     * Trabalho apresentado à unidade curricular de PRA
     * Alunos:
     * Mateus Andreatta
     * Felipe Moreira
     * Julia Louback
     * Pedro Ernesto
     * */

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
        listView.setTextFilterEnabled(true);//filtro pré-definido
        setupSearchView();//inicia o metodo de configurações da searchview
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);// definir se seria usado o icone ou o campo inteiro
        searchView.setOnQueryTextListener(this);//passagem do contexto para usar o searchview
        searchView.setSubmitButtonEnabled(true);//Defini se terá ou nao um o botao de submit
        searchView.setQueryHint("Pesquisar...");//Placeholder da searchbar
    }

    public void fab(View view) {//Metodo do botao de cadastro
        Intent Tela = new Intent(this, CadastroUsuarios.class);
        startActivity(Tela);
    }

    private void PuxarDados() {
        //Definindo um texto para nosso progressDialog e mostrando ele
        progressDialog.setMessage("Carregando dados");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override//Metodo que é executado quando temos a resposta da nossa webservice
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            //Transformando nosso json em um objeto para poder trabalhar com ele
                            JSONObject obj = new JSONObject(response);
                            //Transformando em um array para pegar todos os registros
                            JSONArray jsonArray = obj.getJSONArray("nome");//Este é o nome do json
                            //Laço for para ler todos os registros do json
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                UsuarioConst usuarioConst = new UsuarioConst(jsonObject.getString("nome"),jsonObject.getString("sobrenome"),jsonObject.getString("email"),jsonObject.getString("foto"));
                                //Adicionando cada registros na lista
                                usuarioList.add(usuarioConst);
                            }
                            //Cria o nosso adapter com todos os os registros e ja define ele a nossa listview
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

        requestQueue.getCache().clear();//Limpando o cache da fila
        requestQueue.add(stringRequest);//Adicionando o novo request à fila
    }

    @Override
    public boolean onQueryTextChange(String newText){//Semelhante ao onkeyup do javascript
        if (TextUtils.isEmpty(newText)) {//verifica se o texto digitado é nulo
            /*Como o texto digitado é nulo, limpamos todos os filtros
              Assim mostramos todos os registos  */
            listView.clearTextFilter();
        } else {
            //Caso o texto nao é seja vazio, é definido ele como nosso filto
            listView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query){
        /*
        * Este metodo é usado para o botao de submit,
        * em nosso caso nao ultilizamos esse botao,
        * assim retornamos ele como false, já que o metodo
        * é obrigatorio por conta do implements.
        *
        * Para poder usar ele defina  searchView.setSubmitButtonEnabled(true); em seu onCreate
        * */
        return false;
    }

}
