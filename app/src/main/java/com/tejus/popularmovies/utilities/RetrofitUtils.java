package com.tejus.popularmovies.utilities;

import com.tejus.popularmovies.model.MovieResult;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String LANGUAGE_KEY = "en-US";

    public static MovieResult fetchMovies(String sortMode, String apiKey) {
        MovieResult result = null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitEndpoints endpoints = retrofit.create(RetrofitEndpoints.class);
        Call<MovieResult> call = endpoints.getMovies(sortMode,
                apiKey,
                LANGUAGE_KEY,
                Integer.toString(1));

        try {
            Response<MovieResult> response = call.execute();
            result = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
