package com.tejus.popularmovies.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tejus.popularmovies.R;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private Fragment fragmentPopular;
    private Fragment fragmentRating;
    private Fragment fragmentFavourite;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        fragmentPopular = MainFragment.newInstance(context, context.getString(R.string.sort_popular));
        fragmentRating = MainFragment.newInstance(context, context.getString(R.string.sort_rating));
        fragmentFavourite = FavouriteFragment.newInstance();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return fragmentPopular;
            case 1:
                return fragmentRating;
            case 2:
                return fragmentFavourite;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}