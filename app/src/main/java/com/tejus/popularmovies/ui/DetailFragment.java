package com.tejus.popularmovies.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.databinding.FragmentDetailBinding;
import com.tejus.popularmovies.db.FavouriteMoviesDatabase;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.utilities.AppExecutors;

public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    FragmentDetailBinding mBinding;
    FavouriteMoviesDatabase mDb;
    Movie mMovie;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        mDb = FavouriteMoviesDatabase.getInstance(getContext());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mMovie = bundle.getParcelable(getString(R.string.movie_key));
            mBinding.setMovie(mMovie);
            checkFavouriteDb();
        }
    }

    private void setFavouriteListener() {
        mBinding.toggleFavourite.setOnCheckedChangeListener((buttonView, isChecked) -> toggleFavourite(isChecked));
    }

    private void setFavourite() {
        mBinding.toggleFavourite.setChecked(true);
        //Set listener AFTER the initial check to prevent an additional call to the listener
        setFavouriteListener();
    }

    private void checkFavouriteDb() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (mDb.favouriteMoviesDao().searchMovieById(mMovie.getId()) > 0) {
                AppExecutors.getInstance().mainThread().execute(this::setFavourite);
            }
        });
    }

    private void toggleFavourite(boolean isChecked) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (isChecked) {
                mDb.favouriteMoviesDao().insertMovie(mMovie);
            } else {
                mDb.favouriteMoviesDao().deleteMovieById(mMovie.getId());
            }
        });
    }
}
