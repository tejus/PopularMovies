package com.tejus.popularmovies.ui.main;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.tejus.popularmovies.db.FavouriteMoviesDatabase;
import com.tejus.popularmovies.model.Movie;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> mMovies;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        FavouriteMoviesDatabase db = FavouriteMoviesDatabase.getInstance(this.getApplication());
        mMovies = db.favouriteMoviesDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }
}
