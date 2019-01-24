package com.tejus.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefaultTV = findViewById(R.id.tv_movies);
        mProgressBar = findViewById(R.id.progress_loading);

        mDefaultTV.setText("Sample Text");
    }

    private void showText() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mDefaultTV.setVisibility(View.VISIBLE);
    }

    private void showProgress() {
        mDefaultTV.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void loadJson() {
        showText();
        new FetchMovies().execute("Test");
    }

    public class FetchMovies extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected String doInBackground(String... strings) {

            URL url = NetworkUtils.fetchURL();

            try {
                String jsonMovies = NetworkUtils.fetchMovies(url);
                List<Movie> movies = JsonUtils.getJsonMovieList(jsonMovies);
                return Integer.toString(movies.get(0).getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            showText();
            if (!s.equals("")) {
                mDefaultTV.setText(s);
            }
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
                    showText();
                    loadJson();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.api_not_provided, Toast.LENGTH_SHORT).show();
                    launchApiActivity();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
