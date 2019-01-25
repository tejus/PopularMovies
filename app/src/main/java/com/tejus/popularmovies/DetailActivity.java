package com.tejus.popularmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle("Movie Title Goes Here");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.poster);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int darkMutedColor = palette.getDarkMutedColor(R.attr.colorAccent);
                int vibrantColor = palette.getVibrantColor(R.attr.colorPrimary);
                collapsingToolbar.setCollapsedTitleTextColor(darkMutedColor);
                collapsingToolbar.setContentScrimColor(vibrantColor);
            }
        });
    }
}
