package com.example.josluis.impromusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Adapter;
import android.widget.ListView;

import com.example.josluis.impromusic.Tablas.Challenge;

import java.util.ArrayList;

public class ListChallengeActivity extends AppCompatActivity {

    ListView listViewRetos;
    static ArrayList<Challenge> listaRetos;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_challenge);


    }

    public void cargarRetos() {


    }
}
