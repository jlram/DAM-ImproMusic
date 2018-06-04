package com.example.josluis.impromusic;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.josluis.impromusic.ListChallengeActivity.reto;
import static com.example.josluis.impromusic.LoginActivity.usuario;

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

    public static Participation participacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        nombreReto = findViewById(R.id.textViewTituloReto);
        descrReto = findViewById(R.id.textViewDescrReto);
        listaParticipaciones = findViewById(R.id.listaParticipaciones);
        botonParticipar = findViewById(R.id.buttonParticipar);

        queue = Volley.newRequestQueue(this);

        arrayParticipaciones = new ArrayList<>();

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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChallengeActivity.this);
                final EditText input = new EditText(ChallengeActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.setTitle("Introduce el enlace de youtube");
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=registrarParticipacion&chall=" + reto.getID() + "&music="
                        + usuario.getID() + "&date=2018-06-04&youtube=" + input.getText().toString();
                        consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                Toast.makeText(ChallengeActivity.this, "¡Gracias por participar!", Toast.LENGTH_SHORT).show();
                                cargarPart();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ChallengeActivity.this, "Ya has participado en este reto.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(consulta);

                    }
                });// uncomment this line
                alertDialog.show();
            }
        });

        listaParticipaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                participacion = adapter.getItem(i);
                Intent webIntent = null;
                if (participacion != null) {
                    webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(participacion.getYoutube()));
                }
                view.getContext().startActivity(webIntent);
            }
        });

    }

    private void cargarPart() {

        if (adapter != null) {
            adapter.clear();
        }

        URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=consultaPart&reto=" + reto.getID();
        consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Participation temp = new Gson().fromJson(String.valueOf(obj), Participation.class);
                        arrayParticipaciones.add(temp);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new PartAdapter(ChallengeActivity.this, arrayParticipaciones);

                listaParticipaciones.setAdapter(adapter);

                if (arrayParticipaciones.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Aún no ha participado nadie. ¡Sé el primero!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChallengeActivity.this, "Ha ocurrido un error cargando las participaciones.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);
    }
}
