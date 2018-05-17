package com.example.josluis.impromusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.josluis.impromusic.Adapters.ChallAdapter;
import com.example.josluis.impromusic.Tablas.Challenge;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListChallengeActivity extends AppCompatActivity {

    ListView listViewRetos;
    static ArrayList<Challenge> listaRetos;
    ChallAdapter adapter;

    Request consulta;

    RequestQueue queue;

    String URLConsulta;

    static Challenge reto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_challenge);

        listaRetos = new ArrayList<>();

        queue = Volley.newRequestQueue(this);

        cargaRetos();

        adapter = new ChallAdapter(this, listaRetos);
        listViewRetos = (ListView) findViewById(R.id.ListViewRetos);
        listViewRetos.setAdapter(adapter);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        cargaRetos();
    }

    public void cargaRetos() {

        URLConsulta = "http://10.0.2.2/API_JSON/usuarios.php?accion=consultaRetos";

        consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Challenge temp = new Gson().fromJson(String.valueOf(obj), Challenge.class);
                        listaRetos.add(temp);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ListChallengeActivity.this, "Error de la base de datos.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListChallengeActivity.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);
    }
}
