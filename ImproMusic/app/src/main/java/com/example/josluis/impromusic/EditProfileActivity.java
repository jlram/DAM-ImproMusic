package com.example.josluis.impromusic;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.josluis.impromusic.LoginActivity.usuario;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.josluis.impromusic.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    ImageView mImageView;
    Button mBtnPic;

    EditText mEditTextUser;
    EditText mEditTextPWD;
    EditText mEditTextPWD2;

    Button mBtnSave;

    TextView mTxtUser;
    TextView mTxtPass;

    String URLConsulta;

    JsonObjectRequest consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mImageView = (ImageView) findViewById(R.id.imageProfile);
        mBtnPic = (Button) findViewById(R.id.buttonProfilePic);

        mEditTextUser = (EditText) findViewById(R.id.editProfileName);
        mEditTextPWD = (EditText) findViewById(R.id.editProfilePass);
        mEditTextPWD2 = (EditText) findViewById(R.id.editProfilePass2);

        mBtnSave = (Button) findViewById(R.id.buttonProfileSave);

        mTxtUser = (TextView) findViewById(R.id.textViewProfileUser);
        mTxtPass = (TextView) findViewById(R.id.textViewProfilePass);

        final RequestQueue queue = Volley.newRequestQueue(this);

        if (usuario.getId_pic() == 1) {
            mImageView.setImageResource(R.drawable.avatar1);
        } else if (usuario.getId_pic() == 2) {
            mImageView.setImageResource(R.drawable.avatar2);
        }

        mEditTextUser.setText(usuario.getUsername());


        mBtnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cambiar la foto de perfil
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);

                builder.setTitle("Escoge una foto para tu perfil")
                        .setView(R.layout.image_dialog);

                AlertDialog dialog = builder.create();

                dialog.show();

            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextPWD.getText().toString().equals(mEditTextPWD2.getText().toString())) {
                    //guardar cambios
                    URLConsulta = "http://10.0.2.2/API_JSON/usuarios.php?accion=actualizar&username=" +
                            usuario.getUsername() + "&password=" + mEditTextPWD.getText();

                    consulta = new JsonObjectRequest(Request.Method.GET, URLConsulta, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean modificado = Boolean.parseBoolean(response.getString("estado"));

                                if (modificado) {
                                    Toast.makeText(EditProfileActivity.this, "Usuario modificado con éxito", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditProfileActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(EditProfileActivity.this, "Error de JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(EditProfileActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(consulta);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
