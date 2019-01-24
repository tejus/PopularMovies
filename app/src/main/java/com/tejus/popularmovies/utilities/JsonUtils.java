package com.tejus.popularmovies.utilities;

import android.util.Log;

import com.tejus.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = "JsonUtils";
    private static final String MOVIE_RESULTS = "results";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String POPULARITY = "popularity";
    private static final String RATING = "vote_average";
    private static final String GENRE_ID = "genre_ids";
    private static final String ADULT = "adult";
    private static final String RELEASE_DATE = "release_date";

    public static List<Movie> getJsonMovieList(String jsonString) throws JSONException {

        JSONObject jsonResponse = new JSONObject(jsonString);
        JSONArray movieResults = jsonResponse.getJSONArray(MOVIE_RESULTS);
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < movieResults.length(); i++) {
            JSONObject movie = (JSONObject) movieResults.get(i);
            movieList.add(getJsonBasicMovie(movie));
        }
        return movieList;
    }

    private static Movie getJsonBasicMovie(JSONObject movieObject) throws JSONException {
        int id = movieObject.getInt(ID);
        String posterPath = movieObject.getString(POSTER_PATH);
        boolean adult = movieObject.getBoolean(ADULT);
        Log.v(LOG_TAG, Integer.toString(id) + " " + posterPath + " " + adult);
        return new Movie(id, posterPath, adult);
    }
}
