package com.example.josluis.impromusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.josluis.impromusic.MainActivity.cancion;

public class SongActivity extends AppCompatActivity {

    TextView textViewCancion;
    TextView textViewInfo;

    ImageButton buttonPlay;
    SeekBar seekBar;

    Button buttonCrearReto;
    Button buttonVerRetos;

    boolean play = false;

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

        textViewCancion.setText(cancion.getName());

        textViewInfo.setText("Artista: " + cancion.getAuthor() + "\nAlbum: " + cancion.getAlbum());

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (play) {
                   buttonPlay.setImageResource(android.R.drawable.ic_media_play);
                   play = false;
               } else {
                   buttonPlay.setImageResource(android.R.drawable.ic_media_pause);
                   play = true;
               }
            }
        });

        buttonCrearReto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SongActivity.this, CreateChallengeActivity.class));
            }
        });

        buttonVerRetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SongActivity.this, ListChallengeActivity.class));
            }
        });
    }
}
