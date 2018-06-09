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
import com.example.josluis.impromusic.Tablas.Sugerencia;

import java.util.ArrayList;

public class SugerAdapter extends ArrayAdapter<Sugerencia>{

    TextView sugerencia;

    public SugerAdapter(Context context, ArrayList<Sugerencia> retos) {
        super(context, R.layout.listview_suger_view, retos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_suger_view, parent, false);

        String nombre = getItem(position).getUsername();
        String texto = getItem(position).getContent();

       sugerencia = (TextView) customView.findViewById(R.id.textViewSugerencia);

        sugerencia.setText(nombre+ "\n\n" + texto);

        return customView;
    }
}
