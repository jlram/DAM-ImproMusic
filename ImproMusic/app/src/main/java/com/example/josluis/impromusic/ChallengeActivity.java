package com.example.josluis.impromusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.josluis.impromusic.Adapters.PartAdapter;
import com.example.josluis.impromusic.Tablas.Participation;

import java.util.ArrayList;

import static com.example.josluis.impromusic.ListChallengeActivity.reto;

public class ChallengeActivity extends AppCompatActivity {

    TextView nombreReto;
    TextView descrReto;

    ArrayList<Participation> arrayParticipaciones;
    ListView listaParticipaciones;
    PartAdapter adapter;

    Button botonParticipar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        nombreReto = findViewById(R.id.textViewTituloReto);
        descrReto = findViewById(R.id.textViewDescrReto);
        listaParticipaciones = findViewById(R.id.listaParticipaciones);
        botonParticipar = findViewById(R.id.buttonParticipar);

        /**
         * Uso de la variable "reto", asignada en ListChallengeActivity
         */
        nombreReto.setText(reto.getName());

        descrReto.setText(reto.getDescr());

        /**
         * TODO -> Hacer la clase Participation en funcion de la base de datos
         * TODO-> Añadir un campo URL para poder subir el link de youtube
         * TODO -> Hacer la clase PartAdapter (Copypaste de los otros)
         */

        botonParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}
