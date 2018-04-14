package com.example.josluis.impromusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.josluis.impromusic.R;

public class EditProfileActivity extends AppCompatActivity {

    ImageView mImageView;
    Button mBtnPic;

    EditText mEditTextUser;
    EditText mEditTextPWD;
    EditText mEditTextPWD2;

    Button mBtnSave;

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


        mBtnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cambiar la foto de perfil
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //guardar cambios
            }
        });


    }



}
