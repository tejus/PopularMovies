package com.tejus.popularmovies.utilities;

import android.util.Log;

import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieResult;
import com.tejus.popularmovies.model.Review;
import com.tejus.popularmovies.model.Video;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static final String LOG_TAG = RetrofitUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String LANGUAGE_KEY = "en-US";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static RetrofitEndpoints endpoints = retrofit.create(RetrofitEndpoints.class);

    public static MovieResult<Movie> fetchMovies(String sortMode, String apiKey) {
        Call<MovieResult<Movie>> call = endpoints.getMovies(sortMode,
                apiKey,
                LANGUAGE_KEY,
                Integer.toString(1));
        MovieResult<Movie> movieResult = new MovieResult<>();

        Log.d(LOG_TAG, "Executing network call");
        try {
            Response<MovieResult<Movie>> response = call.execute();
            movieResult = response.body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Network call failed!");
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Returning movieResult from network call");
        return movieResult;
    }


    public static MovieResult<Review> fetchReviews(int id, String apiKey) {
        Call<MovieResult<Review>> call = endpoints.getReviews(id,
                LANGUAGE_KEY,
                apiKey);
        MovieResult<Review> movieResult = new MovieResult<>();

        Log.d(LOG_TAG, "Executing network call");
        try {
            Response<MovieResult<Review>> response = call.execute();
            movieResult = response.body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Network call failed!");
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Returning reviews from network call");
        return movieResult;
    }


    public static MovieResult<Video> fetchVideos(int id, String apiKey) {
        Call<MovieResult<Video>> call = endpoints.getVideos(id,
                LANGUAGE_KEY,
                apiKey);
        MovieResult<Video> movieResult = new MovieResult<>();

        Log.d(LOG_TAG, "Executing network call");
        try {
            Response<MovieResult<Video>> response = call.execute();
            movieResult = response.body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Network call failed!");
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "Returning videos from network call");
        return movieResult;
    }
}
