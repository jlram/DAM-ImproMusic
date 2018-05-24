package com.example.josluis.impromusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

                /**
                 * Comprueba que los campos de nombre y contraseñas no están vacios para continuar.
                 */
                if(mEditTextUser.getText().toString().isEmpty() || mEditTextPWD.getText().toString().isEmpty() || mEditTextPWD2.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();

                } else {

                    if (!mEditTextPWD.getText().toString().equals(mEditTextPWD2.getText().toString())) {
                        mEditTextPWD.setText("");
                        mEditTextPWD2.setText("");

                        Toast.makeText(SignUpActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    } else {
                        //Genera una fecha con el formato de mySQL server
                        android.text.format.DateFormat df = new android.text.format.DateFormat();

                        fecha = df.format("yyyy-MM-dd", new Date());

                        //Consulta que llama al método insertar de nuestra API JSON
                        URLConsulta = "http://" + getResources().getString(R.string.localhost) + "/API_JSON/usuarios.php?accion=insertar&username=" +
                                mEditTextUser.getText() + "&password=" + mEditTextPWD.getText() + "&logdate= " + fecha;

                        consulta = new JsonObjectRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONObject>() {

                            @Override

                            public void onResponse(JSONObject response) {
                                try {

                                    //Pasa a boolean el valor obtenido de estado
                                    boolean registrado = Boolean.parseBoolean(response.getString("estado"));

                                    if (registrado) {
                                        Toast.makeText(SignUpActivity.this, "¡Gracias por registrarte!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Ha habido un error intentando registrarte", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(SignUpActivity.this, "Ha habido un error intentando registrarte", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                mEditTextUser.setText("");
                                mEditTextPWD.setText("");
                                mEditTextPWD2.setText("");
                                Toast.makeText(SignUpActivity.this, "Ya existe un usuario con ese nombre.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Añade la consulta a la RequestQueue
                        queue.add(consulta);
                    }
                }
            }
        });

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
