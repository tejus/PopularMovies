package com.tejus.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieList;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = "DetailActivity";
    private ImageView mPosterImageView;
    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverview;
    private Movie mMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle = findViewById(R.id.tv_title);
        mPosterImageView = findViewById(R.id.iv_poster);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mRating = findViewById(R.id.tv_rating);
        mOverview = findViewById(R.id.tv_overview);

        //Retrieve the movie that was tapped
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        mMovie = MovieList.movieList.get(Integer.valueOf(position));

        //Populate the views with the selected movie
        mTitle.setText(mMovie.getTitle());
        mReleaseDate.setText(mMovie.getReleaseDate());
        String rating = String.format(Locale.getDefault(), "%.1f", mMovie.getRating());
        mRating.setText(rating);
        mOverview.setText(mMovie.getOverview());

        //Load the movie poster
        Picasso.get()
                .load(mMovie.getPosterPath())
                .into(mPosterImageView);
    }
}
