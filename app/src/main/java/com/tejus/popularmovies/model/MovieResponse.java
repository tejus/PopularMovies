package com.tejus.popularmovies.model;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    public List<Movie> movieResponse;

    public MovieResponse() {
        movieResponse = new ArrayList<>();
    }

    public static MovieResponse getGsonMovieList(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();
        MovieResponse movieResponse = gson.fromJson(jsonString, MovieResponse.class);
        return movieResponse;
    }

}

