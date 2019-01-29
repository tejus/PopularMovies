package com.tejus.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tejus.popularmovies.model.MovieList;
import com.tejus.popularmovies.utilities.ApiKey;
import com.tejus.popularmovies.utilities.JsonUtils;
import com.tejus.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private static final String LOG_TAG = "MainActivity";
    private static final int NUM_COLUMNS = 2;
    private static final String SORT_POPULAR = "popular";
    private static final String SORT_RATING = "rating";

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgressBar;
    private String sortMode = SORT_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_loading);
        mRecyclerView = findViewById(R.id.rv_main);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        GridLayoutItemDecoration itemDecoration = new GridLayoutItemDecoration(24, NUM_COLUMNS);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        loadJson();
    }

    public void launchApiActivity() {
        Toast.makeText(getApplicationContext(), R.string.api_not_provided, Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(this, ApiActivity.class);
        startActivity(intent);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void loadJson() {
        if (ApiKey.isApiSet()) {
            new FetchMovies().execute();
        } else {
            launchApiActivity();
        }
    }

    private void switchSortMode(String mode) {
        sortMode = mode;
    }

    public class FetchMovies extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected Void doInBackground(Void... v) {

            URL url = sortMode.equals(SORT_POPULAR) ?
                    NetworkUtils.fetchPopularURL() : NetworkUtils.fetchRatingURL();

            try {
                MovieList.movieList = JsonUtils.getJsonMovieList(NetworkUtils.fetchMovies(url));
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            hideProgressBar();
            mMovieAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("position", Integer.toString(position));
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
        switch (item.getItemId()) {
            case R.id.action_api:
                launchApiActivity();
                return true;
            case R.id.action_refresh:
                loadJson();
                return true;
            case R.id.action_sort_popular:
                switchSortMode(SORT_POPULAR);
                item.setChecked(true);
                loadJson();
                return true;
            case R.id.action_sort_rating:
                switchSortMode(SORT_RATING);
                item.setChecked(true);
                loadJson();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
