package com.example.josluis.impromusic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.josluis.impromusic.MainActivity.cancion;

public class SongActivity extends AppCompatActivity implements View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {

    TextView textViewCancion;
    TextView textViewInfo;

    ImageButton buttonPlay;
    SeekBar seekBar;

    Button buttonCrearReto;
    Button buttonVerRetos;

    /**
     * Declaración del mediaPlayer que vamos a usar para reproducir cada canción segun su URL.
     */
    private MediaPlayer mediaPlayer;

    /**
     * Valor que contiene la duración de la canción en milisegundos.
     */
    private int mediaFileLengthInMilliseconds;

    private final Handler handler = new Handler();

    boolean play = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);



        textViewCancion = (TextView) findViewById(R.id.textViewCancion);
        textViewInfo = (TextView) findViewById(R.id.textViewInfo);

        buttonCrearReto = (Button) findViewById(R.id.buttonCrearReto);
        buttonVerRetos = (Button) findViewById(R.id.buttonVerRetos);

        buttonPlay = (ImageButton) findViewById(R.id.imageButtonMp3);
        seekBar = (SeekBar)   findViewById(R.id.seekBarCancion);

        preparaCancion();
        seekBar.setMax(99);
        seekBar.setOnTouchListener(this);

        textViewCancion.setText(cancion.getName());

        textViewInfo.setText("Artista: " + cancion.getAuthor() + "\nAlbum: " + cancion.getAlbum());

        buttonPlay.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.setDataSource(cancion.getLink());
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

        /**
         * Inicia la actividad para crear un reto.
         */
        buttonCrearReto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SongActivity.this, CreateChallengeActivity.class));
                mediaPlayer.pause();
                buttonPlay.setImageResource(android.R.drawable.ic_media_play);
                play = false;
            }
        });

        /**
         * Inicia la actividad para ver la lista de retos.
         */
        buttonVerRetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SongActivity.this, ListChallengeActivity.class));
                mediaPlayer.pause();
                buttonPlay.setImageResource(android.R.drawable.ic_media_play);
                play = false;
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
}
