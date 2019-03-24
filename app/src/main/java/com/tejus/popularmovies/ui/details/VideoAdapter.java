package com.tejus.popularmovies.ui.details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.databinding.ItemVideoBinding;
import com.tejus.popularmovies.model.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final OnVideoClickListener mVideoClickListener;
    private final OnShareClickListener mShareClickListener;
    private List<Video> mVideos;

    public VideoAdapter(OnVideoClickListener videoClickListener, OnShareClickListener shareClickListener) {
        mVideoClickListener = videoClickListener;
        mShareClickListener = shareClickListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemVideoBinding binding = ItemVideoBinding.inflate(inflater, viewGroup, false);
        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {
        videoViewHolder.mBinding.setVideo(mVideos.get(i));
        videoViewHolder.mBinding.relVideo.setOnClickListener((View v) ->
                mVideoClickListener.onVideoClick(mVideos.get(i).getKey())
        );
        videoViewHolder.mBinding.relShare.setOnClickListener((View v) ->
                mShareClickListener.onShareClick(mVideos.get(i).getKey(), mVideos.get(i).getName()));
    }

    @Override
    public int getItemCount() {
        if (mVideos == null)
            return 0;
        else
            return mVideos.size();
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    public interface OnVideoClickListener {
        void onVideoClick(String key);
    }

    public interface OnShareClickListener {
        void onShareClick(String key, String name);
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ItemVideoBinding mBinding;

        public VideoViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
