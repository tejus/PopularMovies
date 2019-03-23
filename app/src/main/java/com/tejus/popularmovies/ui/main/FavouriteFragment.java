package com.tejus.popularmovies.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.databinding.FragmentMainBinding;
import com.tejus.popularmovies.ui.details.DetailActivity;
import com.tejus.popularmovies.ui.settings.SettingsActivity;

public class FavouriteFragment extends Fragment implements FavouriteAdapter.OnFavouriteClickListener {

    private static final int NUM_COLUMNS = 2;

    private FavouriteAdapter mAdapter;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.rvMain.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));
        binding.rvMain.addItemDecoration(new GridLayoutItemDecoration(24, NUM_COLUMNS));
        binding.rvMain.setHasFixedSize(true);
        mAdapter = new FavouriteAdapter(this);
        binding.rvMain.setAdapter(mAdapter);
        binding.swipeRefreshMain.setEnabled(false);

        setupViewModel();

        return binding.getRoot();
    }

    private void setupViewModel() {
        FavouriteViewModel viewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        viewModel.getMovies().observe(this, (movies -> {
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
