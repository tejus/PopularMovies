package com.tejus.popularmovies.utilities;

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

    /**
     * Populates a list of movies from a JSON response string
     *
     * @param jsonString JSON response from themoviedb.org
     * @return List of movies parsed from the JSON response
     */
    public static List<Movie> getJsonMovieList(String jsonString) throws JSONException {

        JSONObject jsonResponse = new JSONObject(jsonString);
        JSONArray movieResults = jsonResponse.optJSONArray(MOVIE_RESULTS);
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < movieResults.length(); i++) {
            JSONObject movie = (JSONObject) movieResults.get(i);
            movieList.add(getJsonMovie(movie));
        }
        return movieList;
    }

    /**
     * Creates a Movie object from JSON
     *
     * @param movieObject the JSON object for a movie
     * @return Movie object populated with data from the JSON object
     */
    private static Movie getJsonMovie(JSONObject movieObject) {
        int id = movieObject.optInt(ID);
        String title = movieObject.optString(TITLE);
        String posterPath = movieObject.optString(POSTER_PATH);
        String overview = movieObject.optString(OVERVIEW);
        double popularity = movieObject.optDouble(POPULARITY);
        double rating = movieObject.optDouble(RATING);
        JSONArray genreArray = movieObject.optJSONArray(GENRE_ID);
        int[] genreId = new int[genreArray.length()];
        for (int i = 0; i < genreArray.length(); i++) {
            genreId[i] = genreArray.optInt(i);
        }
        boolean adult = movieObject.optBoolean(ADULT);
        String releaseDate = movieObject.optString(RELEASE_DATE);
        return new Movie(id, title, posterPath, overview, popularity, rating, adult, genreId, releaseDate);
    }
}
