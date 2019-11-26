package com.tejus.popularmovies.ui.main;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private String mSortMode;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.mainToolbar);

        mBinding.mainViewPager.setAdapter(new MainPagerAdapter(this, getSupportFragmentManager()));
        mBinding.mainViewPager.setOffscreenPageLimit(2);
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
                changeSortMode(getString(R.string.sort_popular));
                mBinding.mainToolbar.setTitle(R.string.fragment_popular);
                mBinding.mainViewPager.setCurrentItem(0);
                return true;
            case R.id.nav_sort_rating:
                changeSortMode(getString(R.string.sort_rating));
                mBinding.mainToolbar.setTitle(R.string.fragment_top_rated);
                mBinding.mainViewPager.setCurrentItem(1);
                return true;
            case R.id.nav_favourites:
                mBinding.mainToolbar.setTitle(R.string.fragment_favourites);
                mBinding.mainViewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }
}
