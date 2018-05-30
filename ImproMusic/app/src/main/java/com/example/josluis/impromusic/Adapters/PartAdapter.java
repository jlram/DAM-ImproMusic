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
import com.example.josluis.impromusic.Tablas.Participation;

import java.util.ArrayList;

public class PartAdapter extends ArrayAdapter<Participation>{

    public PartAdapter(Context context, ArrayList<Participation> participaciones) {
        super(context, R.layout.listview_part_view, participaciones);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_part_view, parent, false);

        int participante = getItem(position).getID_musician();

        TextView nombre = (TextView) customView.findViewById(R.id.textViewNombrePart);

        nombre.setText(participante);

        return customView;
    }

}
