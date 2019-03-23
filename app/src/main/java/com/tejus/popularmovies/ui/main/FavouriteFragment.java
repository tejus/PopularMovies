package com.tejus.popularmovies.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.databinding.FragmentMainBinding;
import com.tejus.popularmovies.ui.details.DetailActivity;
import com.tejus.popularmovies.ui.settings.SettingsActivity;

public class FavouriteFragment extends Fragment implements FavouriteAdapter.OnFavouriteClickListener {

    private static final String LOG_TAG = FavouriteFragment.class.getSimpleName();

    private static final int NUM_COLUMNS = 2;

    private FragmentMainBinding mBinding;

    private FavouriteAdapter mAdapter;
    private GridLayoutManager layoutManager;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);

        layoutManager = new GridLayoutManager(getActivity(), NUM_COLUMNS);
        GridLayoutItemDecoration itemDecoration = new GridLayoutItemDecoration(24, NUM_COLUMNS);

        mBinding.rvMain.setLayoutManager(layoutManager);
        mBinding.rvMain.addItemDecoration(itemDecoration);
        mBinding.rvMain.setHasFixedSize(true);
        mAdapter = new FavouriteAdapter(this);
        mBinding.rvMain.setAdapter(mAdapter);

        setupViewModel();

        Log.d(LOG_TAG, "onCreateView in FavouriteFragment");
        return mBinding.getRoot();
    }

    private void setupViewModel() {
        FavouriteViewModel viewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        viewModel.getMovies().observe(this, (movies -> {
            Log.d(LOG_TAG, "Received list of movies from ViewModel");
            mAdapter.setMovies(movies);
        }));
    }

    private void launchSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFavouriteClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(getString(R.string.movie_key), mAdapter.getMovieAtPos(position));
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_refresh);
        menuItem.setEnabled(false);
        menuItem.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Launch the API Key activity
            case R.id.action_settings:
                launchSettingsActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}