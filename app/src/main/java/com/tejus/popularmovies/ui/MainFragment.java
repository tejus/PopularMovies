package com.tejus.popularmovies.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.data.MoviePreferences;
import com.tejus.popularmovies.databinding.FragmentMainBinding;
import com.tejus.popularmovies.model.MovieDatabase;
import com.tejus.popularmovies.utilities.AppExecutors;
import com.tejus.popularmovies.utilities.RetrofitUtils;

public class MainFragment extends Fragment implements MainAdapter.OnMovieClickListener {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    private static final int NUM_COLUMNS = 2;
    private static final String SCROLL_POSITION_KEY = "scroll_state";

    //  COMPLETED (3) Add a private data binding member
    private FragmentMainBinding mBinding;

    //  COMPLETED (5) Replace occurrences of the members below with the binding member

    private MainAdapter mMainAdapter;
    private GridLayoutManager layoutManager;

    private int mScrollPosition;
    private String mSortMode;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //  COMPLETED (4) Initialise the data binding member using inflate(inflater, container, false)
        //  instead of Butterknife.bind()
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layoutManager = new GridLayoutManager(getActivity(), NUM_COLUMNS);
        GridLayoutItemDecoration itemDecoration = new GridLayoutItemDecoration(24, NUM_COLUMNS);

        mBinding.rvMain.setLayoutManager(layoutManager);
        mBinding.rvMain.addItemDecoration(itemDecoration);
        mBinding.rvMain.setHasFixedSize(true);
        mMainAdapter = new MainAdapter(this);
        mBinding.rvMain.setAdapter(mMainAdapter);

        setupPreferences();
        checkApiKey();

        if (savedInstanceState != null && savedInstanceState.containsKey(SCROLL_POSITION_KEY)) {
            mScrollPosition = savedInstanceState.getInt(SCROLL_POSITION_KEY);
        } else {
            mScrollPosition = 0;
            showRefreshPrompt();
            fetchMovies();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (layoutManager.getItemCount() == 0) {
            fetchMovies();
        }
    }

    private void checkApiKey() {
        if (MoviePreferences.getApiKey(getActivity()).equals("")) {
            Toast.makeText(getActivity(),
                    R.string.api_not_provided, Toast.LENGTH_SHORT)
                    .show();
            launchSettingsActivity();
        }
    }

    private void setupPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mSortMode = sharedPreferences.getString(getString(R.string.pref_sort_mode_key), getString(R.string.sort_popular));
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_sort_mode_key), mSortMode);
        editor.apply();
    }

    private void launchSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    private void hideProgressBar() {
        mBinding.progressLoading.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        mBinding.progressLoading.setVisibility(View.VISIBLE);
    }

    private void hideRefreshPrompt() {
        mBinding.tvRefreshPrompt.setVisibility(View.GONE);
    }

    private void showRefreshPrompt() {
        mBinding.tvRefreshPrompt.setVisibility(View.VISIBLE);
    }

    /**
     * Executes the Retrofit call to retrieve the list of movies from
     * themoviedb.org and populate the list in MovieDatabase class
     */
    private void fetchMovies() {
        showProgressBar();
        hideRefreshPrompt();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                MovieDatabase.movieResult = RetrofitUtils.fetchMovies(
                        mSortMode,
                        MoviePreferences.getApiKey(getActivity())
                );
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressBar();
                        if (MovieDatabase.movieResult == null ||
                                MovieDatabase.movieResult.getResults().size() == 0) {
                            showRefreshPrompt();
                        } else {
                            mMainAdapter.notifyDataSetChanged();
                        }
                        mBinding.rvMain.smoothScrollToPosition(0);
                    }
                });
            }
        });
    }

    private void changeSortMode(String mode) {
        mSortMode = mode;
        mScrollPosition = 0;
        fetchMovies();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("position", Integer.toString(position));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mScrollPosition = layoutManager.findFirstVisibleItemPosition();
        outState.putInt(SCROLL_POSITION_KEY, mScrollPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        if (mSortMode.equals(getString(R.string.sort_popular))) {
            menu.findItem(R.id.action_sort_popular).setChecked(true);
        } else {
            menu.findItem(R.id.action_sort_rating).setChecked(true);
        }
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
                checkApiKey();
                fetchMovies();
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
