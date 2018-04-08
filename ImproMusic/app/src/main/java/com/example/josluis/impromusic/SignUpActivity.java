package com.example.josluis.impromusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    ImageView mImageView;

    TextView mTextView;
    TextView mTextViewInfo;
    TextView mTextViewLogin;

    EditText mEditTextUser;
    EditText mEditTextPWD;
    EditText mEditTextPWD2;

    Button mBtnSignUp;

    String URLConsulta;
    CharSequence fecha;

    JsonObjectRequest consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mImageView = (ImageView) findViewById(R.id.imageViewReg);
        mTextView = (TextView) findViewById(R.id.textReg);
        mTextViewInfo = (TextView) findViewById(R.id.textInfo);
        mTextViewLogin = (TextView) findViewById(R.id.textLoginReg);
        mEditTextUser = (EditText) findViewById(R.id.editTextUserReg);
        mEditTextPWD = (EditText) findViewById(R.id.editTextPWDReg);
        mEditTextPWD2 = (EditText) findViewById(R.id.editTextPWD2Reg);
        mBtnSignUp = (Button) findViewById(R.id.buttonSignUp);

        final RequestQueue queue = Volley.newRequestQueue(this);

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, "Sign UP", Toast.LENGTH_SHORT).show();

                /**
                 * Comprueba que los campos de nombre y contraseña no están vacios para continuar.
                 * asdasdasdas
                 */
                if(mEditTextUser.getText().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Rellena ambos campos", Toast.LENGTH_SHORT).show();

                } else {

                    android.text.format.DateFormat df = new android.text.format.DateFormat();

                    fecha = df.format("yyyy-MM-dd", new Date());

                    URLConsulta = "http://10.0.2.2/API_JSON/usuarios.php?accion=insertar&username=" +
                            mEditTextUser.getText() +"&password=" + mEditTextPWD.getText() + "&logdate = " + fecha;
                    /**
                    TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO4HEad
                     */
                    consulta = new JsonObjectRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONObject>() {

                        @Override

                        public void onResponse(JSONObject response) {
                            try {

                                boolean registrado = Boolean.parseBoolean(response.getString("estado"));

                                if (registrado) {
                                    Toast.makeText(SignUpActivity.this, "gogogo", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "????????????????", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SignUpActivity.this, "lmao", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast.makeText(SignUpActivity.this, "Error " + error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //Añade la consulta a la RequestQueue
                queue.add(consulta);


            }
        });

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });


    }
}
