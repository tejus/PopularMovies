package com.tejus.popularmovies.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.databinding.FragmentDetailBinding;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieDatabase;

public class DetailFragment extends Fragment {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    FragmentDetailBinding mBinding;

    Movie mMovie;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext())
                    .inflateTransition(android.R.transition.move));
            setEnterTransition(new Fade());
            setExitTransition(new Fade());
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int pos = getArguments().getInt("position");
        //String transitionName = getArguments().getString("transition_name");
        mMovie = MovieDatabase.movieResult.getResults().get(pos);
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBinding.ivPoster.setTransitionName(transitionName);
        }*/
        mBinding.setMovie(mMovie);
    }
}
