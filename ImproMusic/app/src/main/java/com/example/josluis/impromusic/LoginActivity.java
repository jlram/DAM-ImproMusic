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
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.josluis.impromusic.ASyncTask.BackGroundTask;
import com.example.josluis.impromusic.Tablas.Musician;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    ImageView mImageView;

    TextView mTextView;
    TextView mTextView2;
    TextView mTextViewSignUp;
    TextView mTextViewInv;

    EditText mEditTextUser;
    EditText mEditTextPwd;

    Button mButtonLogin;

    String URLConsulta;

    JsonArrayRequest consulta;

    static Musician usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mImageView      = (ImageView) findViewById(R.id.imgLogin);

        mTextView       = (TextView) findViewById(R.id.textLogin);
        mTextView2      = (TextView) findViewById(R.id.textInfo);
        mTextViewSignUp = (TextView) findViewById(R.id.textSignUp);
        mTextViewInv    = (TextView) findViewById(R.id.textInv);

        mEditTextUser   = (EditText) findViewById(R.id.editTextUser);
        mEditTextPwd    = (EditText) findViewById(R.id.editTextPass);

        mButtonLogin    = (Button) findViewById(R.id.buttonLogin);

        final RequestQueue queue = Volley.newRequestQueue(this);

        /**
         * Evento del botón para iniciar sesión. Primero, comprobará que los campos para realizar
         * dicha acción no están vacios. Una vez comprobado, y suponiendo que no lo están, se
         * mandará una consulta a nuestra base de datos comprobando que existe un usuario con
         * los datos introducidos.
         */
        mButtonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * Comprueba que los campos de nombre y contraseña no están vacios para continuar.
                 */
                if(mEditTextUser.getText().equals("") || mEditTextPwd.getText().equals("")) {
                    Toast.makeText(LoginActivity.this, "Rellena ambos campos", Toast.LENGTH_SHORT).show();

                } else {

                    URLConsulta = "http://10.0.2.2/API_JSON/usuarios.php?accion=login&username=" +
                            mEditTextUser.getText() +"&password=" + mEditTextPwd.getText();
                    /**
                     * Comprueba que el array que recogemos no está vacio. Si lo estuviese,
                     * significaría que no existe un usuario con ese nombre y contraseña.
                     *
                     * En caso de no estar vacío, inicia sesión, da la bienvenida a nuestro usuario
                     * y empieza la aplicación.
                     */
                    consulta = new JsonArrayRequest (Request.Method.GET, URLConsulta, null, new Listener<JSONArray>() {

                        @Override

                        public void onResponse(JSONArray response) {
                            try {
                                if (response.length() == 0) { //comprobación del array
                                    mEditTextUser.setText("");
                                    mEditTextPwd.setText("");
                                    Toast.makeText(LoginActivity.this, "Introduce tus datos " +
                                                                       "correctamente",
                                    Toast.LENGTH_SHORT).show();

                                } else {
                                    //coge el objeto json del array
                                    JSONObject c = response.getJSONObject(0);

                                    usuario = new Musician(c.getInt("ID"), c.getString("username"),
                                            c.getString("password"), c.getString("log_date"),
                                            c.getString("user_type"), c.getInt("id_pic"));

                                    Toast.makeText(LoginActivity.this, "Bienvenido, " +
                                            usuario.getUsername(), Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(LoginActivity.this,
                                            Main2Activity.class));

                                    BackGroundTask task = new BackGroundTask(LoginActivity.this);
                                    task.execute();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(LoginActivity.this, "Ha habido un error iniciando sesión", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast.makeText(LoginActivity.this, "Error " + error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //Añade la consulta a la RequestQueue
                queue.add(consulta);
            }
        });

        mTextViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        mTextViewInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Main2Activity.class));
                BackGroundTask task = new BackGroundTask(LoginActivity.this);
                task.execute();
                usuario = new Musician();
                Toast.makeText(LoginActivity.this, "Bienvenido, " +
                        usuario.getUsername(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
