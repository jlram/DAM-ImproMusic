package com.example.josluis.impromusic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.Calendar;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
    int selected = 0;

    Spinner spinnerDate;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterFecha;
    ArrayList<String> arraySpinner;

    String URLConsulta;
    Request consulta;
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

        queue = Volley.newRequestQueue(this);

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
            if (listaCanciones.get(i).getID() == cancion.getID()) {
                selected = i;
            }
        }

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChall.setAdapter(adapter);
        spinnerChall.setSelection(selected);

        adapterFecha = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.fechas));
        adapterFecha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(adapterFecha);

    }

    /**
     * Comprueba el texto del spinner para así crera un objeto Calendar con la fecha actual
     * y sumarle el numero de dias correspondiente para así establecer una fecha de fin
     * del reto.
     * @return
     */
    @SuppressLint("NewApi")
    public CharSequence cogeSpinner() {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        switch (spinnerDate.getSelectedItem().toString()) {
            case "24 Horas" :
                c.add(Calendar.DATE, 1);
                break;
            case "3 Días"   :
                c.add(Calendar.DATE, 3);
                break;
            case  "1 Semana":
                c.add(Calendar.DATE, 7);
                break;
            case "1 Mes"    :
                c.add(Calendar.DATE, 30);
                break;
            case "Elige una duración..." :
                return "ERROR";
        }

        Date fechaSpinner = c.getTime();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        CharSequence fecha_fin = df.format("yyyy-MM-dd", fechaSpinner);

        return fecha_fin;

    }

    /**
     * Comprobamos que los campos de texto no estén ocupados, al igual que con los spinners.
     * En caso de que estén todos los campos correctos, hacemos llamada a la base de datos y
     * creamos un nuevo reto.
     * TODO -> Comprobación de los editText
     */
    public void creaReto() {
        CharSequence fecha2 = cogeSpinner();

        if (editTextNombreChall.getText().toString().isEmpty() ||
                editTextDescrChall.getText().toString().isEmpty() ||
                fecha2.equals("ERROR")) {

                if (fecha2.equals("ERROR")) {
                    Toast.makeText(this, "Por favor, elige una fecha.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Por favor, rellena todos los campos",
                            Toast.LENGTH_SHORT).show();
                }

        } else {
                URLConsulta = "http://10.0.2.2/API_JSON/usuarios.php?accion=crearReto&name=" +
                        editTextNombreChall.getText().toString() + "&id_song=" + cancion.getID() +
                        "&id_user=" + usuario.getID() + "&creat_date=" + fecha + "&fin_date=" +
                        fecha2 + "&descr=" + editTextDescrChall.getText().toString();

                consulta = new JsonObjectRequest(Request.Method.GET, URLConsulta, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(CreateChallengeActivity.this, "¡Tu reto ha sido creado!"
                                        , Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateChallengeActivity.this, "Ha ocurrido un error"
                                , Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(consulta);

            startActivity(new Intent(CreateChallengeActivity.this, ListChallengeActivity.class));
            finish();
        }
    }
}
