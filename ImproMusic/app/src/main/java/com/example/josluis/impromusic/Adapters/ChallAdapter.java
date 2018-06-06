package com.example.josluis.impromusic.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.josluis.impromusic.R;
import com.example.josluis.impromusic.Tablas.Challenge;
import com.example.josluis.impromusic.Tablas.Musician;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChallAdapter extends ArrayAdapter<Challenge> {

    public ChallAdapter(Context context, ArrayList<Challenge> retos) {
        super(context, R.layout.listviewchall_view, retos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listviewchall_view, parent, false);

        String reto = getItem(position).getName();
        int id = getItem(position).getID_musician();

        TextView nombre = (TextView) customView.findViewById(R.id.textViewNombreChall);
        TextView autor = (TextView) customView.findViewById(R.id.textViewAutorChall);

        nombre.setText(reto);

        autor.setText("Creado por: \n");

        consultaPorID(id, autor);

        return customView;
    }

    public void consultaPorID(int partic, final TextView txt) {

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        String URLConsulta = "http://hyperbruh.000webhostapp.com/API_JSON/usuarios.php?accion=consultaPorID&id=" + partic;

        Request consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
                JSONObject obj = null;
                try {
                    obj = response.getJSONObject(0);
                    Musician temp =  new Gson().fromJson(String.valueOf(obj), Musician.class);
                    txt.setText("Creado por: \n" +temp.getUsername());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });
        queue.add(consulta);
    }
}
