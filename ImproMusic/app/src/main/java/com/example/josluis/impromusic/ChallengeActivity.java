package com.example.josluis.impromusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.example.josluis.impromusic.ListChallengeActivity.reto;

public class ChallengeActivity extends AppCompatActivity {

    TextView nombreReto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        nombreReto = findViewById(R.id.textViewTituloReto);

        /**
         * Uso de la variable "reto", asignada en ListChallengeActivity
         */
        nombreReto.setText(reto.getName());

    }
}
