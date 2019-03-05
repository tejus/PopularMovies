package com.tejus.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tejus.popularmovies.db.FavouriteMoviesDatabase;
import com.tejus.popularmovies.model.Movie;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {

    private static final String LOG_TAG = FavouriteViewModel.class.getSimpleName();

    private LiveData<List<Movie>> mMovies;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        FavouriteMoviesDatabase db = FavouriteMoviesDatabase.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Querying database for favourite movies");
        mMovies = db.favouriteMoviesDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }
}
