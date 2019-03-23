package com.tejus.popularmovies.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
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
import com.tejus.popularmovies.db.MovieDatabase;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieResult;
import com.tejus.popularmovies.ui.details.DetailActivity;
import com.tejus.popularmovies.ui.settings.SettingsActivity;
import com.tejus.popularmovies.utilities.AppExecutors;
import com.tejus.popularmovies.utilities.RetrofitUtils;

public class MainFragment extends Fragment implements MainAdapter.OnMovieClickListener {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();

    private static final int NUM_COLUMNS = 2;

    private Context mContext;
    private FragmentMainBinding mBinding;
    private String mSortMode;
    private GridLayoutManager mLayoutManager;
    private MainAdapter mMainAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(Context context, String sortMode) {
        MainFragment fragment = new MainFragment();
        if (!TextUtils.isEmpty(sortMode)) {
            Bundle bundle = new Bundle();
            bundle.putString(context.getString(R.string.pref_sort_mode_key), sortMode);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(getString(R.string.pref_sort_mode_key))) {
            mSortMode = bundle.getString(getString(R.string.pref_sort_mode_key));
        } else
            mSortMode = getString(R.string.sort_popular);
        checkApiKey();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);

        mLayoutManager = new GridLayoutManager(getActivity(), NUM_COLUMNS);
        mMainAdapter = new MainAdapter(mContext, mSortMode, this);

        mBinding.rvMain.setLayoutManager(mLayoutManager);
        mBinding.rvMain.addItemDecoration(new GridLayoutItemDecoration(24, NUM_COLUMNS));
        mBinding.rvMain.setHasFixedSize(true);
        mBinding.rvMain.setAdapter(mMainAdapter);

        mBinding.swipeRefreshMain.setColorSchemeResources(R.color.colorAccent);
        mBinding.swipeRefreshMain.setProgressBackgroundColorSchemeColor(getResources()
                .getColor(R.color.colorPrimary));
        mBinding.swipeRefreshMain.setOnRefreshListener(this::fetchMovies);

        Log.d(LOG_TAG, "onCreateView in MainFragment");
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLayoutManager.getItemCount() == 0) {
            Log.d(LOG_TAG, "Calling fetchMovies from onResume()");
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

    private void launchSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
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
        checkApiKey();
        hideRefreshPrompt();
        mBinding.swipeRefreshMain.setRefreshing(true);
        MovieDatabase.setMovies(mContext, mSortMode, null);
        mMainAdapter.notifyDataSetChanged();
        AppExecutors.getInstance().networkIO().execute(() -> {
            MovieResult<Movie> movieResult = RetrofitUtils.fetchMovies(
                    mSortMode,
                    MoviePreferences.getApiKey(getActivity())
            );
            MovieDatabase.setMovies(mContext, mSortMode, movieResult.getResults());
            AppExecutors.getInstance().mainThread().execute(() -> {
                if (MovieDatabase.getMovies(mContext, mSortMode) == null ||
                        MovieDatabase.getMovies(mContext, mSortMode).size() == 0) {
                    showRefreshPrompt();
                } else {
                    mMainAdapter.notifyDataSetChanged();
                }
                mBinding.rvMain.smoothScrollToPosition(0);
                mBinding.swipeRefreshMain.setRefreshing(false);
            });
        });
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(
                getString(R.string.movie_key),
                MovieDatabase.getMovie(mContext, mSortMode, position)
        );
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
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
                fetchMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
