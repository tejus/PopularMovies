package com.tejus.popularmovies.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.data.MoviePreferences;
import com.tejus.popularmovies.databinding.FragmentReviewsBinding;
import com.tejus.popularmovies.model.MovieResult;
import com.tejus.popularmovies.model.Reviews;
import com.tejus.popularmovies.utilities.AppExecutors;
import com.tejus.popularmovies.utilities.RetrofitUtils;

import java.util.List;

public class ReviewsFragment extends Fragment implements ReviewAdapter.OnReviewClickListener {

    private static final String LOG_TAG = ReviewsFragment.class.getSimpleName();

    private Context mContext;
    private FragmentReviewsBinding mBinding;
    private ReviewAdapter mReviewAdapter;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    public static ReviewsFragment newInstance(Context context, int id) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(context.getString(R.string.movie_key), id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView called");
        mBinding = FragmentReviewsBinding.inflate(inflater, container, false);
        mReviewAdapter = new ReviewAdapter(this);
        mBinding.rvReviews.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvReviews.setAdapter(mReviewAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int id = bundle.getInt(getString(R.string.movie_key));
            fetchReviews(id);
        }
        return mBinding.getRoot();
    }

    private void fetchReviews(int id) {
        AppExecutors.getInstance().networkIO().execute(() -> {
            MovieResult<Reviews> movieResult = RetrofitUtils.fetchReviews(
                    id,
                    MoviePreferences.getApiKey(getActivity())
            );
            AppExecutors.getInstance().mainThread().execute(() -> {
                List<Reviews> reviews = movieResult.getResults();
                if (reviews.size() > 0) {
                    Log.d(LOG_TAG, "Review 1 username: " + reviews.get(0).getAuthor());
                    mReviewAdapter.setReviews(reviews);
                } else
                    mBinding.tvNoReviews.setVisibility(View.VISIBLE);
            });
        });
    }

    @Override
    public void onReviewClick(int position) {

    }
}
