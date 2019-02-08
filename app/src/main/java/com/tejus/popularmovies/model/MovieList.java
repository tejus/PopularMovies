package com.tejus.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieList {

    @SerializedName("results")
    public List<Movie> movieList;

    public MovieList() {
        movieList = new ArrayList<>();
    }
}
