package com.tejus.popularmovies.ui;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.tejus.popularmovies.databinding.FragmentVideosBinding;
import com.tejus.popularmovies.model.MovieResult;
import com.tejus.popularmovies.model.Videos;
import com.tejus.popularmovies.utilities.AppExecutors;
import com.tejus.popularmovies.utilities.NetworkUtils;
import com.tejus.popularmovies.utilities.RetrofitUtils;

import java.util.List;

public class VideosFragment extends Fragment implements VideoAdapter.OnVideoClickListener {

    private static final String LOG_TAG = VideosFragment.class.getSimpleName();

    private Context mContext;
    FragmentVideosBinding mBinding;
    private VideoAdapter mVideoAdapter;

    public VideosFragment() {
        // Required empty public constructor
    }

    public static VideosFragment newInstance(Context context, int id) {
        VideosFragment fragment = new VideosFragment();
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
        mBinding = FragmentVideosBinding.inflate(inflater, container, false);
        mVideoAdapter = new VideoAdapter(this);
        mBinding.rvVideos.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvVideos.setAdapter(mVideoAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int id = bundle.getInt(getString(R.string.movie_key));
            fetchVideos(id);
        }
        return mBinding.getRoot();
    }

    private void fetchVideos(int id) {
        AppExecutors.getInstance().networkIO().execute(() -> {
            MovieResult<Videos> movieResult = RetrofitUtils.fetchVideos(
                    id,
                    MoviePreferences.getApiKey(getActivity())
            );
            AppExecutors.getInstance().mainThread().execute(() -> {
                List<Videos> videos = movieResult.getResults();
                if (videos.size() > 0) {
                    Log.d(LOG_TAG, "Video 1 key: " + videos.get(0).getKey());
                    mVideoAdapter.setVideos(videos);
                } else
                    mBinding.tvNoVideos.setVisibility(View.VISIBLE);
            });
        });
    }

    @Override
    public void onVideoClick(String key) {
        Uri videoPath = NetworkUtils.fetchYoutubeUrl(key);
        Intent launchVideo = new Intent(Intent.ACTION_VIEW, videoPath);
        startActivity(launchVideo);
    }
}
