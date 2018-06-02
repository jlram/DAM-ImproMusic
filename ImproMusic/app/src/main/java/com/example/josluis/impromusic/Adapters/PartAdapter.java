package com.example.josluis.impromusic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josluis.impromusic.R;
import com.example.josluis.impromusic.Tablas.Participation;

import java.util.ArrayList;

public class PartAdapter extends ArrayAdapter<Participation>{

    public PartAdapter(Context context, ArrayList<Participation> participaciones) {
        super(context, R.layout.listview_part_view, participaciones);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.listview_part_view, parent, false);

        final Participation part = getItem(position);
        final int participante = getItem(position).getID_musician();

        TextView nombre = (TextView) customView.findViewById(R.id.textViewNombrePart);
        ImageButton imageButton = customView.findViewById(R.id.imageViewListPart);
        nombre.setText(participante + "");


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(part.getYoutube()));
                view.getContext().startActivity(webIntent);
                Toast.makeText(getContext(), "ee", Toast.LENGTH_SHORT).show();
            }
        });
        return customView;
    }

}
