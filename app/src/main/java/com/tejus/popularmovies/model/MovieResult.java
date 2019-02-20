package com.tejus.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResult {

    @SerializedName("results")
    public List<Movie> movieList;

    public MovieResult() {
        movieList = new ArrayList<>();
    }
}
