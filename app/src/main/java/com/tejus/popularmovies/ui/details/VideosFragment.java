package com.tejus.popularmovies.ui.details;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.R;
import com.tejus.popularmovies.data.MoviePreferences;
import com.tejus.popularmovies.databinding.FragmentVideosBinding;
import com.tejus.popularmovies.model.MovieResult;
import com.tejus.popularmovies.model.Video;
import com.tejus.popularmovies.utilities.AppExecutors;
import com.tejus.popularmovies.utilities.NetworkUtils;
import com.tejus.popularmovies.utilities.RetrofitUtils;

import java.util.List;

public class VideosFragment extends Fragment implements VideoAdapter.OnVideoClickListener, VideoAdapter.OnShareClickListener {

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentVideosBinding.inflate(inflater, container, false);
        mVideoAdapter = new VideoAdapter(this, this);
        mBinding.rvVideos.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvVideos.setAdapter(mVideoAdapter);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(getString(R.string.movie_key))) {
            int id = bundle.getInt(getString(R.string.movie_key));
            fetchVideos(id);
        }
        return mBinding.getRoot();
    }

    private void fetchVideos(int id) {
        AppExecutors.getInstance().networkIO().execute(() -> {
            MovieResult<Video> movieResult = RetrofitUtils.fetchVideos(
                    id,
                    MoviePreferences.getApiKey(getActivity())
            );
            AppExecutors.getInstance().mainThread().execute(() -> {
                List<Video> videos = movieResult.getResults();
                if (videos.size() > 0)
                    mVideoAdapter.setVideos(videos);
                else
                    mBinding.tvNoVideos.setVisibility(View.VISIBLE);
            });
        });
    }

    @Override
    public void onVideoClick(String key) {
        Uri videoPath = NetworkUtils.fetchYoutubeUrl(key);
        Intent watchIntent = new Intent(Intent.ACTION_VIEW, videoPath);
        startActivity(watchIntent);
    }

    @Override
    public void onShareClick(String key, String name) {
        Uri videoPath = NetworkUtils.fetchYoutubeUrl(key);
        String shareText = name + "\n" + videoPath;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.videos_share_with)));
    }
}
