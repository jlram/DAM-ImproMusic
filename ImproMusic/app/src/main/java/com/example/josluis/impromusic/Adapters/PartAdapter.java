package com.example.josluis.impromusic.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.josluis.impromusic.R;
import com.example.josluis.impromusic.Tablas.Participation;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.example.josluis.impromusic.ListChallengeActivity.reto;
import static com.example.josluis.impromusic.LoginActivity.usuario;

public class PartAdapter extends ArrayAdapter<Participation>{
    static final boolean[] ok = new boolean[1];
//    public static final boolean[] votado = {false};
    public static boolean puedeVotar = true;

    /**
     * Variable Participacion para sacar datos.
     */
    static Participation parti;

    public PartAdapter(Context context, ArrayList<Participation> participaciones) {
        super(context, R.layout.listview_part_view, participaciones);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.listview_part_view, parent, false);

        /**
         * Asigna la variable participacion.
         */
        final Participation part; part = getItem(position);

        /**
         * Coge la id del musico que participa en el reto.
         */
        final int participante = getItem(position).getID_musician();

        TextView nombre = (TextView) customView.findViewById(R.id.textViewNombrePart);
        final ImageButton imageButton = customView.findViewById(R.id.imageViewListPart);

        final TextView votos = customView.findViewById(R.id.textViewVotos);

        /**
         * Asigna los datos a cada participacion.
         */
        nombre.setText(participante + "");

        votos.setText(part.getVotes() + "");

        /**
         * Evento del boton para poder puntuar esa participacion y por tanto,
         * añadir a la cuenta de votos en las tablas participaciones y votos.
         */
        imageButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (!puedeVotar) {
                    parti = part;
                    imageButton.setImageResource(R.drawable.blackstar);
                    //RESTAR 1 A LOS VOTOS DE LA BASE DE DATOS
                        eliminaVoto();
                        votos.setText((parti.getVotes()) + "");
                        restaVoto();
                        puedeVotar = true;
                } else {
                    parti = part;
                    registraVoto(imageButton,votos);

                    puedeVotar = false;
                }

            }
        });
        return customView;
    }


    /**
     * Añade el voto a la tabla de participaciones
     */
    public void sumaVoto() {

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        String URLConsulta = "http://hyperbruh.000webhostapp.com/API_JSON/usuarios.php?accion=sumarVoto&part=" + parti.getID();
        System.out.println(URLConsulta);
        Request consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(getContext(), "No ha sido posible votar.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);
    }

    /**
     * Resta el voto a la tabla de participaciones
     */
    public void restaVoto() {

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        String URLConsulta = "http://hyperbruh.000webhostapp.com/API_JSON/usuarios.php?accion=restarVot&part= " + parti.getID();

        Request consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(getContext(), "No ha sido posible borrar el voto.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);
    }

    /**
     * Consulta si hay un voto con los datos introducidos
     */
    public void consultaVoto() {

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        String URLConsulta = "http://hyperbruh.000webhostapp.com/API_JSON/usuarios.php?accion=consultarVoto&chall=" + reto.getID() + "&music=" + usuario.getID() + "&part=" + parti.getID();;

        Request consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });
        queue.add(consulta);
    }

    /**
     * Añade el voto a la tabla voto.
     * @return
     */
    public boolean registraVoto(final ImageButton img, final TextView txt) {
        final RequestQueue queue = Volley.newRequestQueue(getContext());

        String URLConsulta = "http://hyperbruh.000webhostapp.com/API_JSON/usuarios.php?accion=registrarVoto&chall=" + reto.getID() + "&music=" + usuario.getID() + "&part=" + parti.getID();

        Request consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                img.setImageResource(R.drawable.yellowstar);
                sumaVoto();
                txt.setText((parti.getVotes()+1 + ""));
//                Toast.makeText(getContext(), "Voto eliminado con éxito.", Toast.LENGTH_SHORT).show();
//                ok[0] = false;
                System.out.println(response);
                Toast.makeText(getContext(), "Voto registrado con éxito.", Toast.LENGTH_SHORT).show();
//                ok[0] = true;
                puedeVotar = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(getContext(), "No ha sido posible registrar el voto.", Toast.LENGTH_SHORT).show();
//                ok[0] = false;
                puedeVotar = true;
            }
        });
        queue.add(consulta);
        return puedeVotar;
    }

    /**
     * Elimina el voto de la tabla voto
     */
    public void eliminaVoto() {
        final RequestQueue queue = Volley.newRequestQueue(getContext());

        String URLConsulta = "http://hyperbruh.000webhostapp.com/API_JSON/usuarios.php?accion=eliminarVoto&chall=" + reto.getID() + "&music=" + usuario.getID() + "&part=" + parti.getID();

        Request consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(getContext(), "Voto eliminado con éxito.", Toast.LENGTH_SHORT).show();
                ok[0] = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                Toast.makeText(getContext(), "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(consulta);
    }


}
