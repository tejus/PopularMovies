package com.tejus.popularmovies.ui.details;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

        Movie movie = getIntent().getExtras().getParcelable(getString(R.string.movie_key));
        DetailsPagerAdapter pagerAdapter = new DetailsPagerAdapter(this, getSupportFragmentManager(), movie);
        mBinding.detailViewPager.setAdapter(pagerAdapter);
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
