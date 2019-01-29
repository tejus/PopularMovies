package com.tejus.popularmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieList;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = "DetailActivity";
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private ImageView mPosterImageView;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverview;
    private Movie mMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        appBarLayout = findViewById(R.id.app_bar);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = (int) (width * 1.5);

        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, height);
        appBarLayout.setLayoutParams(layoutParams);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPosterImageView = findViewById(R.id.iv_poster);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mRating = findViewById(R.id.tv_rating);
        mOverview = findViewById(R.id.tv_overview);

        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        mMovie = MovieList.movieList.get(Integer.valueOf(position));

        mReleaseDate.setText(mMovie.getReleaseDate());
        String rating = String.format(Locale.getDefault(), "%.1f", mMovie.getRating());
        mRating.setText(rating);
        mOverview.setText(mMovie.getOverview());

        collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle(mMovie.getTitle());
        Picasso.get()
                .load(mMovie.getPosterPath())
                .into(target);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.get().cancelRequest(target);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mPosterImageView.setImageBitmap(bitmap);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @SuppressWarnings("ResourceType")
                @Override
                public void onGenerated(Palette palette) {
                    int darkMutedColor = palette.getDarkMutedColor(R.color.colorAccent);
                    int vibrantColor = palette.getVibrantColor(R.color.colorPrimary);
                    collapsingToolbar.setCollapsedTitleTextColor(darkMutedColor);
                    collapsingToolbar.setContentScrimColor(vibrantColor);
                }
            });
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            //Empty method
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
            collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        }
    };
}
