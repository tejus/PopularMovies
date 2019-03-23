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

    private final OnVideoClickListener mClickListener;
    private List<Video> mVideos;

    public VideoAdapter(OnVideoClickListener clickListener) {
        mClickListener = clickListener;
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
        videoViewHolder.mBinding.getRoot().setOnClickListener((View v) ->
                mClickListener.onVideoClick(mVideos.get(i).getKey())
        );
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

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ItemVideoBinding mBinding;

        public VideoViewHolder(ItemVideoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

}
