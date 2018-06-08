package com.example.josluis.impromusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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

import static com.example.josluis.impromusic.MainActivity.cancion;
import static com.example.josluis.impromusic.MainActivity.desdeCancion;

public class ListChallengeActivity extends AppCompatActivity {

    ListView listViewRetos;
    static ArrayList<Challenge> listaRetos;
    ChallAdapter adapter;

    Request<JSONArray> consulta;

    RequestQueue queue;

    TextView mtxt;

    String URLConsulta;

    public static Challenge reto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_challenge);

        listaRetos = new ArrayList<>();

        queue = Volley.newRequestQueue(this);

        mtxt = findViewById(R.id.textViewTituloChalls);

        /**
         * LLamada al método para cargar la lista al iniciar la actividad.
         */
        cargaRetos();

        listViewRetos = findViewById(R.id.ListViewRetos);

        /**
         * Método que elegir un elemento del listView dará valor a una variable estática "reto"
         * para poder trabajar con ella en la actividad que abrirmos: ChallengeActivity
         */
        listViewRetos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                reto = adapter.getItem(position);
                startActivity(new Intent(ListChallengeActivity.this, ChallengeActivity.class));
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Hacemos una consulta a nuestra API para recibir todos los retos que tenemos.
     * Obtenemos un JSONArray del cual vamos obteniendo JSONObjects y son parseados
     * gracias a Gson para finalmente obtener un objeto Challenge que será añadido
     * a nuestro ArrayList.
     */
    public void cargaRetos() {
        /**
         * URL de nuestra consulta
         */

        if (desdeCancion) {
            URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=consultaRetosPorID&id_song=" + cancion.getID();
        } else {
            URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=consultaRetos";
        }

        consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        /**
                         * Obtención del POJO Challenge
                         */
                        JSONObject obj = response.getJSONObject(i);
                        Challenge temp = new Gson().fromJson(String.valueOf(obj), Challenge.class);
                        listaRetos.add(temp);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ListChallengeActivity.this, "Error de la base de datos.", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter = new ChallAdapter(ListChallengeActivity.this, listaRetos);
                listViewRetos.setAdapter(adapter);
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
