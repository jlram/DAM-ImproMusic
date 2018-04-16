package com.example.josluis.impromusic.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.example.josluis.impromusic.R;

/**
 * Created by jlram on 16/04/2018.
 */

public class MainAdapter extends ArrayAdapter {

    private final Activity context;

    private final Integer[] imageIDArray;

    private final String[] textArray;

    public MainAdapter(Activity context, String[] textArrayParam, Integer[] imageIDArrayParam, Activity context1, Integer[] imageIDArray, String[] textArray){

        super(context , R.layout.listview_view, textArrayParam);

        this.context = context;
        this.imageIDArray = imageIDArrayParam;

        this.textArray = textArrayParam;
    }
}
