package com.tejus.popularmovies.utilities;

import android.net.Uri;

public class NetworkUtils {

    //URL parameters for themoviedb.org API
    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500";

    /**
     * Fetch the poster URL for a movie
     *
     * @param posterPath path for the movie poster
     * @return URL for the full poster path
     */
    public static Uri fetchPosterPath(String posterPath) {
        return Uri.parse(BASE_POSTER_URL + posterPath);
    }
}
