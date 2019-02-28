package com.tejus.popularmovies.utilities;

import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tejus.popularmovies.R;

public class NetworkUtils {

    //URL parameters for themoviedb.org API
    private static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500";

    /**
     * Fetch the poster URL for a movie
     *
     * @param posterPath path for the movie poster
     * @return URL for the full poster path
     */
    private static Uri fetchPosterPath(String posterPath) {
        return Uri.parse(BASE_POSTER_URL + posterPath);
    }

    @BindingAdapter("loadPoster")
    public static void loadPoster(ImageView imageView, String posterPath) {
        Picasso.get()
                .load(fetchPosterPath(posterPath))
                .placeholder(R.color.colorPrimaryDark)
                .error(R.color.colorPrimaryDark)
                .into(imageView);
    }
}
