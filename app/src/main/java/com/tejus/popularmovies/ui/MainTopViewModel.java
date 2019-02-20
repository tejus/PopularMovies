package com.tejus.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.TopMovieDatabase;

import java.util.List;

public class MainTopViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MainTopViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public MainTopViewModel(@NonNull Application application) {
        super(application);
        TopMovieDatabase db = TopMovieDatabase.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Querying database for all movies");
        movies = db.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
