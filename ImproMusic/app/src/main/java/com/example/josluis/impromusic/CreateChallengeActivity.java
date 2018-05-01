package com.example.josluis.impromusic;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.Date;

import static com.example.josluis.impromusic.LoginActivity.usuario;
import static com.example.josluis.impromusic.MainActivity.cancion;
import static com.example.josluis.impromusic.MainActivity.listaCanciones;

public class CreateChallengeActivity extends AppCompatActivity {

    TextView textViewCreateChall;

    EditText editTextNombreChall;
    EditText editTextDescrChall;

    Spinner spinnerChall;
    Spinner spinnerDate;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterFecha;
    ArrayList<String> arraySpinner;

    String URLConsulta;
    Request Consulta;
    RequestQueue queue;

    CharSequence fecha;

    FloatingActionButton fabChall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        textViewCreateChall = (TextView) findViewById(R.id.textViewCreateChall);

        editTextNombreChall = (EditText) findViewById(R.id.editTextNombreChall);
        editTextDescrChall = (EditText) findViewById(R.id.editTextDescrChall);

        spinnerChall = (Spinner) findViewById(R.id.spinnerChall);
        spinnerDate = (Spinner) findViewById(R.id.spinnerDate);

        fabChall = (FloatingActionButton) findViewById(R.id.floatingActionButtonChall);

        arraySpinner = new ArrayList<>();

        rellenaSpinners();

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        fecha = df.format("yyyy-MM-dd", new Date());

        /**
         * Al pulsar el botón haremos llamada a la función de crear reto
         * la cual creará un objeto Challenge en nuestra base de datos.
         * Obtenemos la información de los elementos de la actividad.
         *
         */
        fabChall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                Toast.makeText(CreateChallengeActivity.this, "¡Tu reto ha sido creado!"
                        , Toast.LENGTH_SHORT).show();

                creaReto();

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
    public void rellenaSpinners() {
        for(int i = 0; i < listaCanciones.size(); i++) {
            arraySpinner.add(listaCanciones.get(i).getName());
        }

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChall.setAdapter(adapter);


        adapterFecha = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.fechas));
        adapterFecha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(adapterFecha);

    }

    public void creaReto() {

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        CharSequence fecha_fin = df.format("yyyy-MM-dd", new Date(30));

        Toast.makeText(this, fecha_fin + "", Toast.LENGTH_SHORT).show();
        URLConsulta = "http://10.0.2.2/API_JSON/usuarios.php?accion=crearReto&name=" +
                editTextNombreChall.toString() + "&id_song=" + cancion.getID() +
                "&id_user=" + usuario.getID() + "&creat_date=" + fecha + "&fin_date=" +
                fecha_fin + "&descr=" + editTextDescrChall.getText().toString();

    }

}
