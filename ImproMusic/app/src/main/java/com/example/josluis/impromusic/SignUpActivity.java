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

import org.w3c.dom.Text;

public class SignUpActivity extends AppCompatActivity {

    ImageView mImageView;

    TextView mTextView;
    TextView mTextViewInfo;
    TextView mTextViewLogin;

    EditText mEditTextUser;
    EditText mEditTextPWD;
    EditText mEditTextPWD2;

    Button mBtnSignUp;

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

        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, "Sign UP", Toast.LENGTH_SHORT).show();
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
