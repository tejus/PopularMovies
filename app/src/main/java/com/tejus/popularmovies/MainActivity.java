package com.tejus.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.utilities.ApiKey;
import com.tejus.popularmovies.utilities.JsonUtils;
import com.tejus.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private TextView mDefaultTV;
    private ProgressBar mProgressBar;
    private ImageView mDefaultIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefaultTV = findViewById(R.id.tv_movies);
        mProgressBar = findViewById(R.id.progress_loading);
        mDefaultIV = findViewById(R.id.iv_movies);

        mDefaultIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                startActivity(intent);
            }
        });

        mDefaultTV.setText("Sample Text");
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void loadJson() {
        hideProgressBar();
        new FetchMovies().execute("Test");
    }

    private void showMovie(Movie movie) {
        Uri posterPath = movie.getPosterPath();
        mDefaultTV.setText(movie.getPosterPath().toString());
        Log.v(LOG_TAG, posterPath.toString());
        Picasso.get()
                .load(movie.getPosterPath())
                .into(mDefaultIV);
    }

    public class FetchMovies extends AsyncTask<String, Void, Movie> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected Movie doInBackground(String... strings) {

            URL url = NetworkUtils.fetchURL();

            try {
                String jsonMovies = NetworkUtils.fetchMovies(url);
                List<Movie> movies = JsonUtils.getJsonMovieList(jsonMovies);
                return movies.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            hideProgressBar();
            showMovie(movie);
        }
    }

    public void launchApiActivity() {
        Intent intent = new Intent(this, ApiActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_api:
                launchApiActivity();
                return false;
            case R.id.action_load:
                if (ApiKey.isApiSet()) {
                    hideProgressBar();
                    loadJson();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.api_not_provided, Toast.LENGTH_SHORT).show();
                    launchApiActivity();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
