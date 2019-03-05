package com.tejus.popularmovies.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tejus.popularmovies.R;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private Fragment fragmentPopular;
    private Fragment fragmentRating;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        fragmentPopular = MainFragment.newInstance(context, context.getString(R.string.sort_popular));
        fragmentRating = MainFragment.newInstance(context, context.getString(R.string.sort_rating));
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return fragmentPopular;
            case 1:
                return fragmentRating;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
