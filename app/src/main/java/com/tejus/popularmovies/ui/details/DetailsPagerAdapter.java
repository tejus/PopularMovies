package com.tejus.popularmovies.ui.details;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tejus.popularmovies.model.Movie;

public class DetailsPagerAdapter extends FragmentPagerAdapter {

    private Fragment fragmentDetails;
    private Fragment fragmentReviews;
    private Fragment fragmentVideos;

    public DetailsPagerAdapter(Context context, FragmentManager fm, Movie movie) {
        super(fm);
        fragmentDetails = DetailFragment.newInstance(context, movie);
        fragmentReviews = ReviewsFragment.newInstance(context, movie.getId());
        fragmentVideos = VideosFragment.newInstance(context, movie.getId());
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return fragmentDetails;
            case 1:
                return fragmentReviews;
            case 2:
                return fragmentVideos;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
