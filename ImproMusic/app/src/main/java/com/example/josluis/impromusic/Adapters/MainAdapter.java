package com.example.josluis.impromusic.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josluis.impromusic.R;

import java.util.ArrayList;

/**
 * Created by jlram on 16/04/2018.
 */

public class MainAdapter extends ArrayAdapter<String> {


    public MainAdapter(Context context, ArrayList<String> canciones) {
        super(context, R.layout.listview_view, canciones);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_view, parent, false);

        String cancion = getItem(position);

        TextView nombre = (TextView) customView.findViewById(R.id.textViewNombreCancion);
        ImageView foto = (ImageView) customView.findViewById(R.id.imageViewListaCancion);

        nombre.setText(cancion);

        foto.setImageResource(R.drawable.pop);

        return customView;
    }
}
