package com.tejus.popularmovies.ui.details;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.databinding.ActivityDetailBinding;
import com.tejus.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setSupportActionBar(mBinding.detailToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Movie movie = null;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(getString(R.string.movie_key)))
            movie = getIntent().getExtras().getParcelable(getString(R.string.movie_key));
        else {
            Log.e(LOG_TAG, "No movie found in bundle!");
            finish();
        }

        DetailsPagerAdapter pagerAdapter = new DetailsPagerAdapter(this, getSupportFragmentManager(), movie);
        mBinding.detailViewPager.setAdapter(pagerAdapter);
        mBinding.detailViewPager.setOffscreenPageLimit(2);
        mBinding.detailBottomNav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_details:
                mBinding.detailViewPager.setCurrentItem(0);
                mBinding.detailToolbar.setTitle(R.string.fragment_details);
                return true;
            case R.id.nav_reviews:
                mBinding.detailViewPager.setCurrentItem(1);
                mBinding.detailToolbar.setTitle(R.string.fragment_reviews);
                return true;
            case R.id.nav_videos:
                mBinding.detailViewPager.setCurrentItem(2);
                mBinding.detailToolbar.setTitle(R.string.fragment_videos);
                return true;
        }
        return false;
    }
}
