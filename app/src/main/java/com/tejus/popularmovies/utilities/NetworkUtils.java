package com.tejus.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tejus.popularmovies.R;
import com.tejus.popularmovies.data.MoviePreferences;
import com.tejus.popularmovies.model.MovieResult;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    private static String fetchURL(String sortMode, Context context) {

        String url = HttpUrl.parse(sortMode.equals(context.getString(R.string.sort_popular)) ?
                BASE_POPULAR_URL : BASE_TOP_RATED_URL).newBuilder()
                .addQueryParameter(API_PARAM, MoviePreferences.getApiKey(context))
                .addQueryParameter(LANGUAGE_KEY, LANGUAGE_PARAM)
                .addQueryParameter(PAGE_PARAM, Integer.toString(1))
                .build().toString();
        return url;
    }

    /**
     * Fetches the JSON response from the themoviedb.org API
     *
     * @param sortMode Sort mode, as popular or rating
     * @return String representing the JSON response from themoviedb.org
     */
    public static MovieResult fetchMovies(String sortMode, Context context) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(fetchURL(sortMode, context))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return getGsonMovieList(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    //  TODO (6) Create a method public static void loadPoster(ImageView imageView, String url)
    //  to load images with picasso
    //  TODO (7) Define as a binding adapter with "@BindingAdapter(app:loadPoster)"

    private static MovieResult getGsonMovieList(Response response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();
        MovieResult movieResult = gson.fromJson(response.body().charStream(), MovieResult.class);
        return movieResult;
    }
}
