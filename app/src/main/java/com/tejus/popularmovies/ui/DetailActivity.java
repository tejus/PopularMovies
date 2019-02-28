package com.tejus.popularmovies.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.databinding.ActivityDetailBinding;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieDatabase;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        //Retrieve the movie that was tapped
        Intent intent = getIntent();
        String position = intent.getStringExtra("position");
        Movie mMovie = MovieDatabase.movieResult.getResults().get(Integer.valueOf(position));
        mBinding.setMovie(mMovie);
    }
}
