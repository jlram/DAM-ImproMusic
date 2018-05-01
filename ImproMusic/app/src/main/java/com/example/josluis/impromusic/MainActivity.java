package com.example.josluis.impromusic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.josluis.impromusic.Adapters.MainAdapter;
import com.example.josluis.impromusic.Tablas.Song;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.josluis.impromusic.LoginActivity.usuario;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView listViewCanciones;
    static ArrayList<Song> listaCanciones;
    MainAdapter adapter;

    String URLConsulta;

    Request consulta;

    RequestQueue queue;

    static Song cancion;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        listaCanciones = new ArrayList<>();

        queue = Volley.newRequestQueue(this);

        //Método declarado abajo.
        cargaCanciones();

        /**
         * Creamos un nuevo Adapter personalizado. Una vez hecho, declaramos nuestro ListView y le
         * asignamos nuestro adaptador.
         */
        adapter = new MainAdapter(this, listaCanciones);
        listViewCanciones = (ListView) findViewById(R.id.listViewCanciones);
        listViewCanciones.setAdapter(adapter);

        /**
         * Metodo que le da funcionalidad al botón flotante. Haremos una llamada a la actividad de sugerencias.
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contactar con los administradores", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_text = (TextView)hView.findViewById(R.id.headerText);
        TextView nav_type = (TextView)hView.findViewById(R.id.headerType);

        /**
         * Muestra la información principal de nuestro usuario.
         */
        nav_text.setText(usuario.getUsername() + "\n\nMiembro desde: "
                        + usuario.getLog_date());
        nav_type.setText(usuario.getUser_type());

        /**
         * Evento que al seleccionar una canción, nos dirigimos a una nueva actividad en la que
         * se mostrará información más detallada sobre la misma.
         */
        listViewCanciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                cancion = adapter.getItem(position);
                startActivity(new Intent(MainActivity.this, SongActivity.class));
            }
        });
    }

    /**
     * Método que se llama al pulsón el botón de navegacion BACK.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        /**
         * Borro la actividad de login para evitar cerrar sesión sin querer.
         */
        LoginActivity.fa.finish();
    }

    /**
     * Evento de creación del menú.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

 /*
    NO ESTOY SEGURO PERO CREO QUE ESTO ES USELESS AF

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    /**
     * Evento de selección de items en la barra lateral.
     * Cada opción llevará al usuario a una nueva actividad.
     * @param item
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_canciones) {

        } else if (id == R.id.nav_retos) {

        } else if (id == R.id.nav_perfil) {
            /**
             * Comprueba que el ID no es el del usuario invitado (ID = 2)
             */
            if (usuario.getID() == 2) {
                Toast.makeText(this, "¡Registrate para acceder a esta opcion!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
            }

        } else if (id == R.id.nav_config) {

        } else if (id == R.id.nav_compartir) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Método propio en el que hago una consulta a la base de datos pidiendo todas las canciones,
     * las cuales se añadirán a nuestro ArrayList para más tarde mostrarse en el ListView de nuestra
     * actividad principal.
     */
    public void cargaCanciones() {

        URLConsulta = "http://10.0.2.2/API_JSON/usuarios.php?accion=consultaCanciones";

        consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Song temp = new Gson().fromJson(String.valueOf(obj), Song.class);
                        listaCanciones.add(temp);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error de la base de datos.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);
    }
}
