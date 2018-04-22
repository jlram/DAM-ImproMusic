package com.example.josluis.impromusic;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateChallengeActivity extends AppCompatActivity {

    TextView textViewCreateChall;

    EditText editTextNombreChall;
    EditText editTextDescrChall;

    Spinner spinnerChall;
    CalendarView calendarViewChall;

    FloatingActionButton fabChall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        textViewCreateChall = (TextView) findViewById(R.id.textViewCreateChall);

        editTextNombreChall = (EditText) findViewById(R.id.editTextNombreChall);
        editTextDescrChall = (EditText) findViewById(R.id.editTextDescrChall);

        spinnerChall = (Spinner) findViewById(R.id.spinnerChall);

        calendarViewChall = (CalendarView) findViewById(R.id.calendarViewChall);

        fabChall = (FloatingActionButton) findViewById(R.id.floatingActionButtonChall);

    }
}
