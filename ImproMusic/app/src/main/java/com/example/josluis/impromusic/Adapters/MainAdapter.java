package com.example.josluis.impromusic.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josluis.impromusic.R;
import com.example.josluis.impromusic.Tablas.Song;

import java.util.ArrayList;

public class MainAdapter extends ArrayAdapter<Song> {

    public MainAdapter(Context context, ArrayList<Song> canciones) {
        super(context, R.layout.listview_view, canciones);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_view, parent, false);

        String cancion = getItem(position).getName();
        String autor = getItem(position).getAuthor();

        TextView nombre = (TextView) customView.findViewById(R.id.textViewNombreCancion);
        TextView artista = (TextView) customView.findViewById(R.id.textViewArtista);
        ImageView foto = (ImageView) customView.findViewById(R.id.imageViewListaCancion);

        nombre.setText(cancion);
        artista.setText(autor);
        //foto.setImageResource(R.drawable.music);

        return customView;
    }
}
