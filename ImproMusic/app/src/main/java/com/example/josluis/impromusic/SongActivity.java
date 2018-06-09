package com.example.josluis.impromusic;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.josluis.impromusic.LoginActivity.usuario;
import static com.example.josluis.impromusic.MainActivity.cancion;
import static com.example.josluis.impromusic.MainActivity.desdeCancion;
import static com.example.josluis.impromusic.MainActivity.mediaPlayer;

public class SongActivity extends AppCompatActivity implements View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {

    TextView textViewCancion;
    TextView textViewInfo;

    ImageView cover;

    ImageButton buttonPlay;
    SeekBar seekBar;

    ImageButton buttonCrearReto;
    ImageButton buttonVerRetos;
    ImageButton buttonLoop;
    ImageButton buttonDelete;

    RequestQueue queue;

    /**
     * Valor que contiene la duración de la canción en milisegundos.
     */
    private int mediaFileLengthInMilliseconds;

    private final Handler handler = new Handler();

    boolean play = false;
    boolean loop = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        textViewCancion = (TextView) findViewById(R.id.textViewCancion);
        textViewInfo = (TextView) findViewById(R.id.textViewInfo);

        buttonCrearReto = (ImageButton) findViewById(R.id.buttonCrearReto);
        buttonVerRetos = (ImageButton) findViewById(R.id.buttonVerRetos);

        buttonPlay = (ImageButton) findViewById(R.id.imageButtonMp3);
        seekBar = (SeekBar)   findViewById(R.id.seekBarCancion);

        cover = findViewById(R.id.imageViewCover);
        buttonLoop = findViewById(R.id.imageButtonLoop);

        buttonDelete = findViewById(R.id.buttonDelete);

        queue = Volley.newRequestQueue(this);

        preparaCancion();
        seekBar.setMax(99);
        seekBar.setOnTouchListener(this);

        if (usuario.getUser_type().equals("admin")) {
            buttonDelete.setVisibility(View.VISIBLE);
        } else {
            buttonDelete.setVisibility(View.INVISIBLE);
        }

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(SongActivity.this)
                        .setTitle("Confirma esta acción")
                        .setMessage("¿Deseas borrar la canción " + cancion.getName() + "?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                borrarCancion();
                            }})
                        .setNegativeButton("No", null).show();


            }
        });

        /**
         * Asigna los valores de la cancion a los distintos elementos de la acitividad:
         */

        textViewCancion.setText(cancion.getName());
        textViewInfo.setText(cancion.getAuthor() + " - " + cancion.getAlbum());

        /**
         * Uso de la libreria Picasso. Descarga la imagen segun la URL proporcionada
         * y la inserta en nuestro imageView.
         */
        Picasso.get().load("http://" + cancion.getCover()).resize(275, 275).into(cover);

        buttonPlay.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.setDataSource("http://" + cancion.getLink());
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
                if (play) {
                   buttonPlay.setImageResource(android.R.drawable.ic_media_play);
                   mediaPlayer.pause();
                   play = false;
               } else {
                   buttonPlay.setImageResource(android.R.drawable.ic_media_pause);
                   mediaPlayer.start();
                   play = true;
               }
                primarySeekBarProgressUpdater();
            }
        });

        buttonLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loop) {
                    mediaPlayer.setLooping(false);
                    buttonLoop.setImageResource(R.drawable.loop_off);
                    loop = false;
                } else {
                    mediaPlayer.setLooping(true);
                    buttonLoop.setImageResource(R.drawable.loop);
                    loop = true;
                }
            }
        });

        /**
         * Inicia la actividad para crear un reto.
         */
        buttonCrearReto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (usuario.getUser_type().equals("invitado")) {
                    Toast.makeText(SongActivity.this, "¡Regístrate para acceder a esta opción!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(SongActivity.this, CreateChallengeActivity.class));
                    mediaPlayer.pause();
                    buttonPlay.setImageResource(android.R.drawable.ic_media_play);
                    play = false;
                }
            }
        });

        /**
         * Inicia la actividad para ver la lista de retos.
         */
        buttonVerRetos.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if (usuario.getUser_type().equals("invitado")) {
                    Toast.makeText(SongActivity.this, "¡Regístrate para acceder a esta opción!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(SongActivity.this, ListChallengeActivity.class));
                    mediaPlayer.pause();
                    buttonPlay.setImageResource(android.R.drawable.ic_media_play);
                    play = false;
                    desdeCancion = true;
                }
            }
        });
    }

    /**
     * Inicializa el mediaPlayer
     */
    public void preparaCancion() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    /**
     * Actualiza la posición de la seekBar en función del avance de la canción.
     */
    private void primarySeekBarProgressUpdater() {
        seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds)*100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }
    }

    /**
     * Evento de cada vez que tocamos la seekBar cambia el segundo de la canción en proporción.
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.seekBarCancion){
            if(mediaPlayer.isPlaying()){
                SeekBar sb = (SeekBar)v;
                int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                mediaPlayer.seekTo(playPositionInMillisecconds);
            }
        }
        return false;
    }

    /**
     * Method which updates the SeekBar secondary progress by current song loading from URL position
     * @param mp
     * @param percent
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
    }

    /**
     * Cuando acaba la canción acaba se vuelve a poner el icono de play
     * y se prepara para volver a reproducirla.
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/
        buttonPlay.setImageResource(android.R.drawable.ic_media_play);
        play = false;
    }

    /**
     * Al salir de la actividad termina la reproducción de la canción.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.pause();
    }

    public void borrarCancion() {

        String URLConsulta = "http://hyperbruh.000webhostapp.com/API_JSON/usuarios.php?accion=eliminarCancion&id_song= " + cancion.getID();

        JsonArrayRequest consulta = new JsonArrayRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject c = response.getJSONObject(0);
                    if (c.getString("estado").equals("true")) {
                        Toast.makeText(SongActivity.this, "Canción eliminada con éxito", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SongActivity.this, "No se ha podido eliminar la canción", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SongActivity.this, "Ha ocurrido un error eliminando la canción.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(consulta);

    }

}
