package com.tejus.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mDefaultTV;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefaultTV = findViewById(R.id.tv_movies);
        mProgressBar = findViewById(R.id.progress_loading);

        mDefaultTV.setText("Sample Text");
    }
}
