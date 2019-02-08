package com.tejus.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tejus.popularmovies.R;
import com.tejus.popularmovies.data.MoviePreferences;
import com.tejus.popularmovies.model.MovieList;

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

    /**
     * Creates the requested URL
     *
     * @param sortMode Key to retrieve movies by popularity or rating
     * @return URL for the query
     */
    private static URL fetchURL(String sortMode, Context context) {
        Uri uri = Uri.parse(sortMode.equals(context.getString(R.string.sort_popular)) ?
                BASE_POPULAR_URL : BASE_TOP_RATED_URL).buildUpon()
                .appendQueryParameter(API_PARAM, MoviePreferences.getApiKey(context))
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
     * @param sortMode Sort mode, as popular or rating
     * @return String representing the JSON response from themoviedb.org
     */
    public static MovieList fetchMovies(String sortMode, Context context) {

        URL url = fetchURL(sortMode, context);
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return getGsonMovieList(scanner.next());
            } else return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
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

    private static MovieList getGsonMovieList(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();
        MovieList movieList = gson.fromJson(jsonString, MovieList.class);
        return movieList;
    }
}
