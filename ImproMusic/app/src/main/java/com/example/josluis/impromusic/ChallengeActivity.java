package com.example.josluis.impromusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.josluis.impromusic.Adapters.PartAdapter;
import com.example.josluis.impromusic.Tablas.Participation;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.example.josluis.impromusic.ListChallengeActivity.reto;

public class ChallengeActivity extends AppCompatActivity {

    TextView nombreReto;
    TextView descrReto;

    ArrayList<Participation> arrayParticipaciones;
    ListView listaParticipaciones;
    PartAdapter adapter;

    String URLConsulta;
    RequestQueue queue;

    Request consulta;

    Button botonParticipar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        nombreReto = findViewById(R.id.textViewTituloReto);
        descrReto = findViewById(R.id.textViewDescrReto);
        listaParticipaciones = findViewById(R.id.listaParticipaciones);
        botonParticipar = findViewById(R.id.buttonParticipar);

        queue = Volley.newRequestQueue(this);

        cargarPart();

        /**
         * Uso de la variable "reto", asignada en ListChallengeActivity
         */
        nombreReto.setText(reto.getName());

        descrReto.setText(reto.getDescr());

        /**
         * TODO -> Hacer la clase Participation en funcion de la base de datos
         * TODO-> Añadir un campo URL para poder subir el link de youtube
         * TODO -> Hacer la clase PartAdapter (Copypaste de los otros)
         */

        botonParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void cargarPart() {

        URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=consultaPart";
        consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(ChallengeActivity.this, "Ha entrado en el evento", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChallengeActivity.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);
    }
}
