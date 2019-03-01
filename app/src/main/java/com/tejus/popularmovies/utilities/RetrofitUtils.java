package com.tejus.popularmovies.utilities;

import android.util.Log;

import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieResult;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static final String LOG_TAG = RetrofitUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String LANGUAGE_KEY = "en-US";
    private static MovieResult movieResult = null;
    private static Movie movie = null;
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static RetrofitEndpoints endpoints = retrofit.create(RetrofitEndpoints.class);

    public static MovieResult fetchMovies(String sortMode, String apiKey) {
        Call<MovieResult> call = endpoints.getMovies(sortMode,
                apiKey,
                LANGUAGE_KEY,
                Integer.toString(1));

        Log.d(LOG_TAG, "Executing network call");
        try {
            Response<MovieResult> response = call.execute();
            movieResult = response.body();
        } catch (IOException e) {
            Log.d(LOG_TAG, "Network call failed!");
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Returning movieResult from network call");
        return movieResult;
    }

    public static Movie fetchMovie(int id, String apiKey) {
        Call<Movie> call = endpoints.getMovie(id, apiKey);

        Log.d(LOG_TAG, "Executing network call");
        try {
            Response<Movie> response = call.execute();
            movie = response.body();
        } catch (IOException e) {
            Log.d(LOG_TAG, "Network call failed!");
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Returning movie from network call");
        return movie;
    }
}
