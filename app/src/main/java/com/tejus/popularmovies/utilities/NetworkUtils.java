package com.tejus.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String BASE_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String BASE_TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated";
    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w185";
    private static final String API_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String LANGUAGE_KEY = "en-US";
    private static final String PAGE_PARAM = "page";

    public static URL fetchURL() {
        Uri uri = Uri.parse(BASE_POPULAR_URL).buildUpon()
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

    public static Uri fetchPosterPath(String posterPath) {
        Uri posterUri = Uri.parse(BASE_POSTER_URL + posterPath);
        return posterUri;
    }
}
