package com.example.magdi.rottentomatoes;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MoviesActivity extends ActionBarActivity {

    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;
    RottenTomatoesClient client;
    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        ArrayList<MovieModel> aMovies = new ArrayList<MovieModel>();
        adapterMovies = new BoxOfficeMoviesAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
// Fetch the data remotely
        fetchBoxOfficeMovies();
        setupMovieSelectedListener();

    }

    private void fetchBoxOfficeMovies() {
        client = new RottenTomatoesClient();
        client.getBoxOfficeMovies(new JsonHttpResponseHandler() {

            public void onSuccess(int code, Header [] headers ,JSONObject body) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = body.getJSONArray("movies");
                    // Parse json array into array of model objects
                    ArrayList<MovieModel> movies = MovieModel.fromJson(items);
                    // Load model objects into the adapter
                    for (MovieModel movie : movies) {
                        adapterMovies.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                Intent i = new Intent(MoviesActivity.this, BoxOfficeDetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }
}

