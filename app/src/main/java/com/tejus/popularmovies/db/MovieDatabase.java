package com.tejus.popularmovies.db;

import android.content.Context;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieResult;

public class MovieDatabase {

    //Lists of movies stored statically for other classes to access
    private static MovieResult popularMovieResult = new MovieResult();
    private static MovieResult ratingMovieResult = new MovieResult();

    public static MovieResult getMovies(Context context, String sortMode) {
        return sortMode.equals(context.getString(R.string.sort_popular)) ?
                popularMovieResult : ratingMovieResult;
    }

    public static Movie getMovie(Context context, String sortMode, int position) {
        return sortMode.equals(context.getString(R.string.sort_popular)) ?
                popularMovieResult.getResults().get(position) :
                ratingMovieResult.getResults().get(position);
    }

    public static void setMovies(Context context, String sortMode, MovieResult movieResult) {
        if (sortMode.equals(context.getString(R.string.sort_popular))) {
            popularMovieResult = movieResult;
        } else if (sortMode.equals(context.getString(R.string.sort_rating))) {
            ratingMovieResult = movieResult;
        }
    }
}
