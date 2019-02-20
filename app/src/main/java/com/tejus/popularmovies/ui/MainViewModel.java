package com.tejus.popularmovies.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tejus.popularmovies.data.MoviePreferences;
import com.tejus.popularmovies.model.MovieDatabase;
import com.tejus.popularmovies.model.MovieResult;
import com.tejus.popularmovies.utilities.AppExecutors;
import com.tejus.popularmovies.utilities.RetrofitUtils;

public class MainViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MainViewModel.class.getSimpleName();
    private MutableLiveData<MovieResult> movies = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.d(LOG_TAG, "Initialising networkIO executor");
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                MovieResult result = RetrofitUtils.fetchMovies("popular",
                        MoviePreferences.getApiKey(getApplication().getApplicationContext()));
                Log.d(LOG_TAG, "Initialising mainThread executor");
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(LOG_TAG, "Setting received movies to the MutableLiveData<MovieResult> instance");
                        movies.setValue(result);
                        MovieDatabase.movieList.addAll(result.getResults());
                    }
                });
            }
        });
    }

    public MutableLiveData<MovieResult> getMovies() {
        return movies;
    }
}
