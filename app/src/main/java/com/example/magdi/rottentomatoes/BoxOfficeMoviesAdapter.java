package com.example.magdi.rottentomatoes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by magdi on 1/29/15.
 */
public class BoxOfficeMoviesAdapter extends ArrayAdapter<MovieModel> {
    public BoxOfficeMoviesAdapter(Context context, ArrayList<MovieModel> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieModel movie = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }
        // Lookup views within item layout
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        TextView tvCast = (TextView) convertView.findViewById(R.id.tvCast);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        // Populate the data into the template view using the data object
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText("Score: " + movie.getCriticsScore() + "%");
        tvCast.setText(movie.getCastList());
        Picasso.with(getContext()).load(movie.getPosterUrl()).into(ivPosterImage);
        // Return the completed view to render on screen
        return convertView;
    }
}

