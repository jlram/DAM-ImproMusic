package com.example.josluis.impromusic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.example.josluis.impromusic.Adapters.SugerAdapter;
import com.example.josluis.impromusic.Tablas.Sugerencia;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListSugerActivity extends AppCompatActivity {

    String URLConsulta;
    RequestQueue queue;
    JsonArrayRequest consulta;
    ArrayList<Sugerencia> listaSugerencias;
    SugerAdapter adapter;

    TextView textViewSugerencias;
    ListView listViewSugerencias;
    static Sugerencia sugerencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_suger);

        queue = Volley.newRequestQueue(this);

        listaSugerencias = new ArrayList<>();

        textViewSugerencias = findViewById(R.id.textViewSugerencia);
        listViewSugerencias = findViewById(R.id.listviewSugerencias);

        listViewSugerencias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sugerencia = listaSugerencias.get(i);

                new AlertDialog.Builder(ListSugerActivity.this)
                        .setTitle("¿Descartar sugerencia?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                borrarSugerencia();
                            }})
                        .setNegativeButton("No", null).show();

                return true;
            }
        });

    }

    @Override
    protected void onPostResume() {
        cargaSugerencias();
        super.onPostResume();
    }

    public void borrarSugerencia() {

        String URLConsulta = "http://hyperbruh.000webhostapp.com/API_JSON/usuarios.php?accion=eliminarSugerencia&id= " + sugerencia.getID();

        JsonArrayRequest consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject c = response.getJSONObject(0);
                    if (c.getString("estado").equals("true")) {
                        Toast.makeText(ListSugerActivity.this, "Sugerencia descartada con éxito", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ListSugerActivity.this, "No se ha podido descartar la sugerencia", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListSugerActivity.this, "Ha ocurrido un error descartando la sugerencia.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);

    }

    public void cargaSugerencias() {

        URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=consultaSugerencias";

        consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        if (response.length() != 0) {
                            JSONObject obj = response.getJSONObject(i);
                            Sugerencia temp = new Gson().fromJson(String.valueOf(obj), Sugerencia.class);
                            listaSugerencias.add(temp);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ListSugerActivity.this, "Error de la base de datos.", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter = new SugerAdapter(ListSugerActivity.this, listaSugerencias);
                listViewSugerencias.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListSugerActivity.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);
    }
}
