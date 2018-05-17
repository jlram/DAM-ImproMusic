package com.example.josluis.impromusic.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.josluis.impromusic.R;
import com.example.josluis.impromusic.Tablas.Challenge;

import java.util.ArrayList;

public class ChallAdapter extends ArrayAdapter<Challenge> {

    public ChallAdapter(Context context, ArrayList<Challenge> retos) {
        super(context, R.layout.listviewchall_view, retos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listviewchall_view, parent, false);

        String reto = getItem(position).getName();
        int id = getItem(position).getID_musician();

        TextView nombre = (TextView) customView.findViewById(R.id.textViewNombreChall);
        TextView autor = (TextView) customView.findViewById(R.id.textViewAutorChall);

        nombre.setText(reto);

        autor.setText("Creado por: \n" + id);

        return customView;
    }
}
