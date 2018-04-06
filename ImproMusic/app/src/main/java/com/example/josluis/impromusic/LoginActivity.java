package com.example.josluis.impromusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    ImageView mImageView;

    TextView mTextView;
    TextView mTextView2;
    TextView mTextViewSignUp;
    TextView mTextViewInv;

    Button mButtonLogin;

    String URLConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Por favor, inicia sesión", Toast.LENGTH_SHORT).show();

        mImageView = (ImageView) findViewById(R.id.imgLogin);

        mTextView = (TextView) findViewById(R.id.textLogin);
        mTextView2 = (TextView) findViewById(R.id.textInfo);
        mTextViewSignUp = (TextView) findViewById(R.id.textSignUp);
        mTextViewInv = (TextView) findViewById(R.id.textInv);

        mButtonLogin = (Button) findViewById(R.id.buttonLogin);

        URLConsulta = "http://10.0.2.2/API_JSON/usuarios.php?accion=consulta";

        final RequestQueue queue = Volley.newRequestQueue(this);

        /**
         *
         */
        final JsonArrayRequest consulta = new JsonArrayRequest (Request.Method.GET, URLConsulta, null, new Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject c = response.getJSONObject(0);

                    Toast.makeText(LoginActivity.this, "Bienvenido, " + c.getString("username"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(LoginActivity.this, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Inicio de sesión", Toast.LENGTH_SHORT).show();
            }
        });

        mTextViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                Toast.makeText(LoginActivity.this, "Registrarme", Toast.LENGTH_SHORT).show();
            }
        });

        mTextViewInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Invitado", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(consulta);
    }
}
