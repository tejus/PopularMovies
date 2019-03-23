package com.tejus.popularmovies.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private String mSortMode;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.mainToolbar);

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
        mBinding.mainViewPager.setAdapter(pagerAdapter);
        mBinding.mainBottomNav.setOnNavigationItemSelectedListener(this);
    }

    private void changeSortMode(String mode) {
        if (mode.equals(mSortMode)) return;
        mSortMode = mode;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_sort_popular:
                Log.d(LOG_TAG, "switch case nav_sort_popular matched");
                changeSortMode(getString(R.string.sort_popular));
                mBinding.mainToolbar.setTitle(R.string.fragment_popular);
                mBinding.mainViewPager.setCurrentItem(0);
                return true;
            case R.id.nav_sort_rating:
                Log.d(LOG_TAG, "switch case nav_sort_rating matched");
                changeSortMode(getString(R.string.sort_rating));
                mBinding.mainToolbar.setTitle(R.string.fragment_top_rated);
                mBinding.mainViewPager.setCurrentItem(1);
                return true;
            case R.id.nav_favourites:
                Log.d(LOG_TAG, "switch case nav_favourites matched");
                mBinding.mainToolbar.setTitle(R.string.fragment_favourites);
                mBinding.mainViewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }
}
