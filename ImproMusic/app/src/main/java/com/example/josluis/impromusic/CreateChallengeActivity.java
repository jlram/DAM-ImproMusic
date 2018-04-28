package com.example.josluis.impromusic;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josluis.impromusic.Adapters.MainAdapter;

import java.util.ArrayList;

import static com.example.josluis.impromusic.LoginActivity.usuario;
import static com.example.josluis.impromusic.MainActivity.listaCanciones;

public class CreateChallengeActivity extends AppCompatActivity {

    TextView textViewCreateChall;

    EditText editTextNombreChall;
    EditText editTextDescrChall;

    Spinner spinnerChall;
    ArrayAdapter<String> adapter;
    ArrayList<String> arraySpinner;
    CalendarView calendarViewChall;

    FloatingActionButton fabChall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        textViewCreateChall = (TextView) findViewById(R.id.textViewCreateChall);

        editTextNombreChall = (EditText) findViewById(R.id.editTextNombreChall);
        editTextDescrChall = (EditText) findViewById(R.id.editTextDescrChall);

        spinnerChall = (Spinner) findViewById(R.id.spinnerChall);

        calendarViewChall = (CalendarView) findViewById(R.id.calendarViewChall);

        fabChall = (FloatingActionButton) findViewById(R.id.floatingActionButtonChall);

        arraySpinner = new ArrayList<>();

        rellenaSpinner();






        /**
         * Botón que nos hará ir al siguiente fragment.
         *
         *   String nombre = editTextNombreChall.getText().toString();
         *  String descripcion = editTextDescrChall.getText().toString();
         *   int ID_musc = usuario.getID();
         *
         */
        fabChall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

            //TODO -> CAMBIAR DE FRAGMENT
                Toast.makeText(CreateChallengeActivity.this, "cambio de fragment"
                        , Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * Recorremos todos los nombres de canciones y vamos introduciendo sus nombres
     * de manera dinámica.
     *
     * Una vez hecho esto, creamos un adapter para nuestro spinner, le asignamos
     * una vista para ver los items de una manera más clara y lo asignamos
     * a nuestro spinner.
     */
    public void rellenaSpinner() {
        for(int i = 0; i < listaCanciones.size(); i++) {
            arraySpinner.add(listaCanciones.get(i).getName());
        }

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerChall.setAdapter(adapter);
    }

}
