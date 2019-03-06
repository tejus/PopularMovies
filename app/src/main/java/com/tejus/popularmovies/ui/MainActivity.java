package com.tejus.popularmovies.ui;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        setSupportActionBar(mBinding.toolbar);

        MainPagerAdapter mPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mPagerAdapter);
        mBinding.bottomNav.setOnNavigationItemSelectedListener(this);
        setupPreferences();
        mBinding.viewPager
                .setCurrentItem(mSortMode.equals(getString(R.string.sort_popular)) ? 0 : 1);
    }

    private void setupPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSortMode = sharedPreferences
                .getString(getString(R.string.pref_sort_mode_key), getString(R.string.sort_popular));
        mBinding.bottomNav.setSelectedItemId(mSortMode.equals(getString(R.string.sort_popular))
                ? R.id.nav_sort_popular : R.id.nav_sort_rating);
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.pref_sort_mode_key), mSortMode);
        editor.apply();
    }

    private void changeSortMode(String mode) {
        if (mode.equals(mSortMode)) return;
        mSortMode = mode;
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_sort_popular:
                Log.d(LOG_TAG, "switch case nav_sort_popular matched");
                changeSortMode(getString(R.string.sort_popular));
                mBinding.toolbar.setTitle(R.string.fragment_popular);
                mBinding.viewPager.setCurrentItem(0);
                return true;
            case R.id.nav_sort_rating:
                Log.d(LOG_TAG, "switch case nav_sort_rating matched");
                changeSortMode(getString(R.string.sort_rating));
                mBinding.toolbar.setTitle(R.string.fragment_top_rated);
                mBinding.viewPager.setCurrentItem(1);
                return true;
            case R.id.nav_favourites:
                Log.d(LOG_TAG, "switch case nav_favourites matched");
                mBinding.toolbar.setTitle(R.string.fragment_favourites);
                mBinding.viewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }
}
