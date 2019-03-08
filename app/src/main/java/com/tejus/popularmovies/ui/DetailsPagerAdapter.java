package com.tejus.popularmovies.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tejus.popularmovies.model.Movie;

public class DetailsPagerAdapter extends FragmentPagerAdapter {

    private Fragment fragmentDetails;

    public DetailsPagerAdapter(Context context, FragmentManager fm, Movie movie) {
        super(fm);
        fragmentDetails = DetailFragment.newInstance(context, movie);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return fragmentDetails;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
