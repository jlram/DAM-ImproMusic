package com.example.josluis.impromusic.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josluis.impromusic.MainActivity;
import com.example.josluis.impromusic.R;
import com.example.josluis.impromusic.Tablas.Song;

import java.util.ArrayList;

import es.claucookie.miniequalizerlibrary.EqualizerView;

import static com.example.josluis.impromusic.MainActivity.cancion;
import static com.example.josluis.impromusic.MainActivity.mediaPlayer;

public class MainAdapter extends ArrayAdapter<Song> {

    public MainAdapter(Context context, ArrayList<Song> canciones) {
        super(context, R.layout.listview_view, canciones);
    }

    static boolean play = false;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.listview_view, parent, false);

        String autor = getItem(position).getAuthor();
        String rola = getItem(position).getName();

        TextView nombre = (TextView) customView.findViewById(R.id.textViewNombreCancion);
        TextView artista = (TextView) customView.findViewById(R.id.textViewArtista);
        final ImageButton foto = customView.findViewById(R.id.imageViewListaCancion);

        final EqualizerView equalizer = (EqualizerView) customView.findViewById(R.id.equalizer_view);
       // Whenever you want to tart the animation



        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying() && cancion.getID() != getItem(position).getID()) {
                    Toast.makeText(getContext(), "Ya hay otra canción sonando", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.setSong(getItem(position));

                    if (play) {
                        foto.setImageResource(android.R.drawable.ic_media_play);
                        equalizer.stopBars();
                        equalizer.setVisibility(View.INVISIBLE);
                        play = false;
                        mediaPlayer.stop();
                        mediaPlayer.seekTo(0);
                    } else {
                        foto.setImageResource(android.R.drawable.ic_media_pause);
                        equalizer.setVisibility(View.VISIBLE);
                        equalizer.animateBars();
                        play = true;
                        mediaPlayer.start();
                    }
                }
            }
        });

        nombre.setText(rola);
        artista.setText(autor);
        //foto.setImageResource(R.drawable.music);

        return customView;
    }



}
