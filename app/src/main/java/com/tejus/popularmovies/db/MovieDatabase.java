package com.tejus.popularmovies.db;

import android.content.Context;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabase {

    //Lists of movies stored statically for other classes to access
    private static List<Movie> popularMovieResult = new ArrayList<>();
    private static List<Movie> ratingMovieResult = new ArrayList<>();

    public static List<Movie> getMovies(Context context, String sortMode) {
        return sortMode.equals(context.getString(R.string.sort_popular)) ?
                popularMovieResult : ratingMovieResult;
    }

    public static Movie getMovie(Context context, String sortMode, int position) {
        return sortMode.equals(context.getString(R.string.sort_popular)) ?
                popularMovieResult.get(position) :
                ratingMovieResult.get(position);
    }

    public static void setMovies(Context context, String sortMode, List<Movie> movieResult) {
        if (sortMode.equals(context.getString(R.string.sort_popular))) {
            popularMovieResult = movieResult;
        } else if (sortMode.equals(context.getString(R.string.sort_rating))) {
            ratingMovieResult = movieResult;
        }
    }
}
