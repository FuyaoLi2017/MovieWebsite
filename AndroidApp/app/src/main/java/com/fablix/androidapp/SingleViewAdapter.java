package com.fablix.androidapp;

import android.app.Person;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;


public class SingleViewAdapter extends ArrayAdapter<SingleMovie> {
    private ArrayList<SingleMovie> movies;

    private static class ViewHolder {
        TextView titleView;
        TextView YearView;
        TextView DirectorView;
        TextView GenresView;
        TextView StarsView;

    }

    public SingleViewAdapter(ArrayList<SingleMovie> movies, Context context) {
        super(context, R.layout.layout_listview_row, movies);
        this.movies = movies;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.layout_listview_row, parent, false);

        SingleMovie singlemov = movies.get(position);
        ViewHolder viewHolder=new ViewHolder();
        Log.d("Debug","Working");


        viewHolder.titleView = (TextView)convertView.findViewById(R.id.title);
        viewHolder.YearView = (TextView)convertView.findViewById(R.id.year);
        viewHolder.DirectorView = (TextView)convertView.findViewById(R.id.director);
        viewHolder.GenresView = (TextView)convertView.findViewById(R.id.genre);
        viewHolder.StarsView = (TextView)convertView.findViewById(R.id.star);
        viewHolder.titleView.setText(singlemov.getTitle());
        viewHolder.YearView.setText(singlemov.getYear());
        viewHolder.DirectorView.setText(singlemov.getDirector());
        viewHolder.GenresView.setText(singlemov.getGenres().toString().replaceAll("\\[|\\]", ""));
        viewHolder.StarsView.setText(singlemov.getStars().toString().replaceAll("\\[|\\]", ""));

        return convertView;
    }
}