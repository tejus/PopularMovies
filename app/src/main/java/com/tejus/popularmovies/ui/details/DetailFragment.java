package com.tejus.popularmovies.ui.details;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    private FragmentDetailBinding mBinding;
    private FavouriteMoviesDatabase mDb;
    private Movie mMovie;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Context context, Movie movie) {
        DetailFragment fragment = new DetailFragment();
        if (movie != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(context.getString(R.string.movie_key), movie);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = FavouriteMoviesDatabase.getInstance(getContext());
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(getString(R.string.movie_key))) {
            mMovie = bundle.getParcelable(getString(R.string.movie_key));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.toggleFavourite.setBackgroundTintList(
                    getResources().getColorStateList(R.color.colorAccent)
            );
        }
        mBinding.setMovie(mMovie);
        setupFavouriteButton();
        return mBinding.getRoot();
    }

    private void setFavouriteListener() {
        mBinding.toggleFavourite.setOnCheckedChangeListener((buttonView, isChecked) ->
                toggleFavourite(isChecked)
        );
    }

    private void checkIfFavourite(int count) {
        if (count > 0) {
            mBinding.toggleFavourite.setChecked(true);
        }
        //Set listener AFTER the initial check to prevent an additional call to the listener
        setFavouriteListener();
    }

    private void setupFavouriteButton() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            final int count = mDb.favouriteMoviesDao().searchMovieById(mMovie.getId());
            AppExecutors.getInstance().mainThread().execute(() -> checkIfFavourite(count));
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
