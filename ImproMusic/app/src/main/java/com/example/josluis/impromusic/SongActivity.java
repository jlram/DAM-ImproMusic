package com.example.josluis.impromusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.josluis.impromusic.MainActivity.cancion;

public class SongActivity extends AppCompatActivity {

    TextView textViewCancion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        textViewCancion = (TextView) findViewById(R.id.textViewCancion);

        textViewCancion.setText(cancion.getName());
    }
}
