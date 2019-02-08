package com.tejus.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tejus.popularmovies.data.MoviePreferences;
import com.tejus.popularmovies.model.MovieDatabase;
import com.tejus.popularmovies.utilities.NetworkUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener {

    private static final String LOG_TAG = "MainActivity";
    private static final int NUM_COLUMNS = 2;
    private static final String SCROLL_POSITION_KEY = "scroll_state";

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_loading)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_refresh_prompt)
    TextView mRefreshPrompt;

    private MovieAdapter mMovieAdapter;
    private GridLayoutManager layoutManager;

    private String mSortMode;
    private int mScrollPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SCROLL_POSITION_KEY)) {
                mScrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY);
            }
        }

        layoutManager = new GridLayoutManager(this, NUM_COLUMNS);
        GridLayoutItemDecoration itemDecoration = new GridLayoutItemDecoration(24, NUM_COLUMNS);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        setupPreferences();

        showRefreshPrompt();
        loadJson();
    }

    private void setupPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSortMode = sharedPreferences.getString(getString(R.string.pref_sort_mode_key), getString(R.string.sort_popular));
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_sort_mode_key), mSortMode);
        editor.apply();
    }

    private void launchSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideRefreshPrompt() {
        mRefreshPrompt.setVisibility(View.GONE);
    }

    private void showRefreshPrompt() {
        mRefreshPrompt.setVisibility(View.VISIBLE);
    }

    /**
     * Executes the AsyncTask to retrieve the list of movies from
     * themoviedb.org and populate the list in MovieDatabase class
     */
    private void loadJson() {
        if (!MoviePreferences.getApiKey(this).equals("")) {
            new FetchMovies().execute();
        } else {
            Toast.makeText(getApplicationContext(), R.string.api_not_provided, Toast.LENGTH_SHORT)
                    .show();
            launchSettingsActivity();
        }
    }

    private void changeSortMode(String mode) {
        mSortMode = mode;
        mScrollPosition = 0;
        loadJson();
    }

    /**
     * AsyncTask to fetch movies from themoviedb.org, using the sort mode
     * set in mSortMode, and store them in a list in the MovieDatabase class
     */
    public class FetchMovies extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
            hideRefreshPrompt();
        }

        @Override
        protected Void doInBackground(Void... v) {
            MovieDatabase.movieList = NetworkUtils.fetchMovies(mSortMode, getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            hideProgressBar();
            if (MovieDatabase.movieList == null || MovieDatabase.movieList.movieList.size() == 0) {
                showRefreshPrompt();
            } else {
                mMovieAdapter.notifyDataSetChanged();
            }

            /*
             * LayoutManager.findFirstVisibleItemPosition() returns '-1' when the screen is off
             * so make sure to set scroll position to zero in that case, to prevent a crash:
             * (IllegalArgumentException: Invalid target position)
             */
            mRecyclerView.smoothScrollToPosition(mScrollPosition >= 0 ? mScrollPosition : 0);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("position", Integer.toString(position));
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mScrollPosition = layoutManager.findFirstVisibleItemPosition();
        outState.putInt(SCROLL_POSITION_KEY, mScrollPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        if (mSortMode.equals(getString(R.string.sort_popular))) {
            menu.findItem(R.id.action_sort_popular).setChecked(true);
        } else {
            menu.findItem(R.id.action_sort_rating).setChecked(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Launch the API Key activity
            case R.id.action_settings:
                launchSettingsActivity();
                return true;
            //Manually reload the movies
            case R.id.action_refresh:
                loadJson();
                return true;
            //Changes sort mode to popular and reloads the movies
            case R.id.action_sort_popular:
                item.setChecked(true);
                changeSortMode(getString(R.string.sort_popular));
                return true;
            //Changes sort mode to rating and reloads the movies
            case R.id.action_sort_rating:
                item.setChecked(true);
                changeSortMode(getString(R.string.sort_rating));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
