package com.tejus.popularmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tejus.popularmovies.R;
import com.tejus.popularmovies.model.Movie;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    private static final String PARCEL_KEY = "movie";

    @BindView(R.id.iv_poster)
    ImageView mPosterImageView;

    @BindView(R.id.tv_title)
    TextView mTitle;

    @BindView(R.id.tv_release_date)
    TextView mReleaseDate;

    @BindView(R.id.tv_rating)
    TextView mRating;

    @BindView(R.id.tv_overview)
    TextView mOverview;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        //Retrieve the movie that was tapped
        Intent intent = getIntent();
        mMovie = intent.getExtras().getParcelable(PARCEL_KEY);

        //Populate the views with the selected movie
        mTitle.setText(mMovie.getTitle());
        mReleaseDate.setText(mMovie.getReleaseDate());
        String rating = String.format(Locale.getDefault(), "%.1f", mMovie.getRating());
        mRating.setText(rating);
        mOverview.setText(mMovie.getOverview());

        //Load the movie poster
        Picasso.get()
                .load(mMovie.getPosterPathUri())
                .into(mPosterImageView);
    }
}
