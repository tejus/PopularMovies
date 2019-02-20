package com.tejus.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.PopularMovieDatabase;

import java.util.List;

public class MainPopularViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MainPopularViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public MainPopularViewModel(@NonNull Application application) {
        super(application);
        PopularMovieDatabase db = PopularMovieDatabase.getInstance(this.getApplication());
        Log.d(LOG_TAG, "Querying database for all movies");
        movies = db.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
