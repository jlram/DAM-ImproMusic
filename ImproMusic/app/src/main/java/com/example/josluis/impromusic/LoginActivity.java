package com.example.josluis.impromusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ImageView mImageView;

    TextView mTextView;
    TextView mTextView2;
    TextView mTextViewSignUp;
    TextView mTextViewInv;

    Button mButtonLogin;



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


    }
}
