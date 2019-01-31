package com.tejus.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    //URL parameters for themoviedb.org API
    private static final String BASE_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String BASE_TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated";
    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500";
    private static final String API_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String LANGUAGE_KEY = "en-US";
    private static final String PAGE_PARAM = "page";

    private static final String SORT_POPULAR = "popular";
    private static final String SORT_RATING = "rating";

    public static URL fetchPopularURL() {
        return fetchURL(SORT_POPULAR);
    }

    public static URL fetchRatingURL() {
        return fetchURL(SORT_RATING);
    }

    /**
     * Creates the requested URL
     *
     * @param sortMode Key to retrieve movies by popularity or rating
     * @return URL for the query
     */
    private static URL fetchURL(String sortMode) {
        Uri uri = Uri.parse(sortMode.equals(SORT_POPULAR) ? BASE_POPULAR_URL : BASE_TOP_RATED_URL).buildUpon()
                .appendQueryParameter(API_PARAM, ApiKey.getApiKey())
                .appendQueryParameter(LANGUAGE_KEY, LANGUAGE_PARAM)
                .appendQueryParameter(PAGE_PARAM, Integer.toString(1))
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Fetches the JSON response from the themoviedb.org API
     *
     * @param url URL as built by fetchURL()
     * @return String representing the JSON response from themoviedb.org
     */
    public static String fetchMovies(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else return null;
        } finally {
            urlConnection.disconnect();
        }
    }

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
