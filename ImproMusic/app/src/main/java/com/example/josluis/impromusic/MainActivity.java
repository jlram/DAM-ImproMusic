package com.example.josluis.impromusic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.josluis.impromusic.Adapters.MainAdapter;
import com.example.josluis.impromusic.Tablas.Song;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.josluis.impromusic.LoginActivity.usuario;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {

    ListView listViewCanciones;
    static ArrayList<Song> listaCanciones;
    MainAdapter adapter;

    String URLConsulta;

    Request consulta;

    RequestQueue queue;

    public static Song cancion;

    NavigationView navigationView;

    static boolean desdeCancion;

    /**
     * Declaración del mediaPlayer que vamos a usar para reproducir cada canción segun su URL.
     */
    public static MediaPlayer mediaPlayer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        desdeCancion = false;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        listaCanciones = new ArrayList<>();

        navigationView = findViewById(R.id.nav_view);

        queue = Volley.newRequestQueue(this);

        /**
         * Marca como seleccionado el elemento "Canciones"
         * de nuestro menú lateral.
         */
        navigationView.getMenu().getItem(0).setChecked(true);

        /**
         * Cambia la transparencia del menú.
         */
        navigationView.getBackground().setAlpha(200);

        //Método declarado abajo.
        //cargaCanciones();

        //Preparación del mediaPlayer
        preparaMediaPlayer();

        /**
         * Creamos un nuevo Adapter personalizado. Una vez hecho, declaramos nuestro ListView y le
         * asignamos nuestro adaptador.
         */
        listViewCanciones = (ListView) findViewById(R.id.listViewCanciones);

        /**
         * Metodo que le da funcionalidad al botón flotante. Haremos una llamada a la actividad de sugerencias.
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        if(usuario.getUser_type().equals("admin")) {
            fab.setImageResource(R.drawable.add);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    meterCancion();
                }
            });

        } else if (usuario.getUser_type().equals("normal")) {
            navigationView.getMenu().findItem(R.id.nav_sugerencias).setVisible(false);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mandarSugerencia();
                }
            });
        } else {
            navigationView.getMenu().findItem(R.id.nav_sugerencias).setVisible(false);
            fab.setVisibility(View.INVISIBLE);
        }


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
        nav_text.setText(usuario.getUsername());
        nav_type.setText("Usuario de tipo " + usuario.getUser_type());

        /**
         * Evento que al seleccionar una canción, nos dirigimos a una nueva actividad en la que
         * se mostrará información más detallada sobre la misma.
         */
        listViewCanciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                cancion = adapter.getItem(position);
                startActivity(new Intent(MainActivity.this, SongActivity.class));

                mediaPlayer.stop();
            }
        });
    }

    /**
     * Método que se llama al pulsar el botón de navegacion BACK.
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

    @Override
    protected void onPostResume() {
        cargaCanciones();
        navigationView.getMenu().getItem(0).setChecked(true);
        preparaMediaPlayer();
        super.onPostResume();
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
            if (usuario.getUser_type().equals("invitado")) {
                Toast.makeText(this, "¡Regístrate para acceder a esta opción!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(MainActivity.this, ListChallengeActivity.class));
            }
        } else if (id == R.id.nav_perfil) {
            /**
             * Comprueba que el ID no es el del usuario invitado (ID = 2)
             */
            if (usuario.getUser_type().equals("invitado")) {
                Toast.makeText(this, "¡Regístrate para acceder a esta opción!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
            }

//        } else if (id == R.id.nav_config) {

        } else if (id == R.id.nav_sugerencias) {
            startActivity(new Intent(MainActivity.this, ListSugerActivity.class));
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

        if (adapter != null) {
            adapter.clear();
        }


        URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=consultaCanciones";

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

                adapter = new MainAdapter(MainActivity.this, listaCanciones);
                listViewCanciones.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);
    }
    public void preparaMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    //TODO -> HACER LA PREVIEW DE CANCIONES Y ASIGNARLAS
    public static void setSong(Song song) {
        cancion = song;
        try {
            mediaPlayer.setDataSource("http://" + song.getLink() + ".preview.mp3");
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    public void meterCancion() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Introduce los datos de la canción");
//        dialog.setMessage("Completa los campos");
        Context context = MainActivity.this;
        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(15, 15, 15, 15);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nombreBox = new EditText(context);
        nombreBox.setHint("Nombre");
        layout.addView(nombreBox);

        final EditText authorBox = new EditText(context);
        authorBox.setHint("Autor");
        layout.addView(authorBox);

        final EditText albumBox = new EditText(context);
        albumBox.setHint("Album");
        layout.addView(albumBox);

        final EditText genreBox = new EditText(context);
        genreBox.setHint("Género");
        layout.addView(genreBox);

        final EditText coverBox = new EditText(context);
        coverBox.setHint("Portada");
        layout.addView(coverBox);

        final EditText linkBox = new EditText(context);
        linkBox.setHint("Enlace");
        layout.addView(linkBox);

        dialog.setView(layout); // Again this is a set method, not add

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (nombreBox.getText().toString().isEmpty() || authorBox.getText().toString().isEmpty() ||
                    albumBox.getText().toString().isEmpty() || genreBox.getText().toString().isEmpty() ||
                    coverBox.getText().toString().isEmpty() || linkBox.getText().toString().isEmpty()) {

                    Toast.makeText(MainActivity.this, "Por favor, rellena todos los campos.", Toast.LENGTH_SHORT).show();

                } else {
                    URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=meterCancion&name=" +
                            nombreBox.getText().toString() + "&author=" +
                            authorBox.getText().toString() + "&album=" +
                            albumBox.getText().toString() + "&genre=" +
                            genreBox.getText().toString() + "&cover=" +
                            coverBox.getText().toString() + "&link=" + linkBox.getText().toString();

                    consulta = new JsonObjectRequest(Request.Method.GET, URLConsulta, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(MainActivity.this, "Canción añadida"
                                            , Toast.LENGTH_SHORT).show();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Ha ocurrido un error"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(consulta);
                }
            }
        });

        dialog.show();

    }

    public void mandarSugerencia() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Manda una sugerencia a los administradores.");

        Context context = MainActivity.this;
        LinearLayout layout = new LinearLayout(context);
        layout.setPadding(15, 15, 15, 15);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText sugBox = new EditText(context);
        sugBox.setHint("");
        sugBox.setLines(4);
        sugBox.setMaxLines(5);
        sugBox.setGravity(Gravity.LEFT | Gravity.TOP);
        sugBox.setHorizontallyScrolling(false);
        layout.addView(sugBox);

        dialog.setView(layout); // Again this is a set method, not add

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (sugBox.getText().toString().isEmpty()) {

                    Toast.makeText(MainActivity.this, "Por favor, rellena todos los campos.", Toast.LENGTH_SHORT).show();

                } else {
                    URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=mandarSugerencia" +
                            "&username=" + usuario.getUsername() +
                            "&content=" + sugBox.getText().toString();

                    consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Toast.makeText(MainActivity.this, "Sugerencia enviada. ¡Gracias por contribuir!"
                                            , Toast.LENGTH_SHORT).show();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "Ha ocurrido un error enviando tu sugerencia."
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(consulta);
                }
            }
        });

        dialog.show();

    }


}
