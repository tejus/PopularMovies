package com.tejus.popularmovies.ui;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tejus.popularmovies.databinding.ItemMovieBinding;
import com.tejus.popularmovies.model.Movie;
import com.tejus.popularmovies.model.MovieDatabase;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MovieViewHolder> {

    private final OnMovieClickListener mClickHandler;

    public MainAdapter(OnMovieClickListener clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemMovieBinding binding = ItemMovieBinding.inflate(inflater, viewGroup, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie movie = MovieDatabase.movieResult.getResults().get(i);
        movieViewHolder.mBinding.setMovie(movie);
        ViewCompat.setTransitionName(movieViewHolder.mBinding.ivPosterThumb, String.valueOf(movie.getId()));
        movieViewHolder.mBinding.getRoot().setOnClickListener((View v) -> {
            mClickHandler.onMovieClick(movieViewHolder.getAdapterPosition(), movieViewHolder.mBinding.ivPosterThumb);
        });
    }

    @Override
    public int getItemCount() {
        if (MovieDatabase.movieResult == null) {
            return 0;
        } else
            return MovieDatabase.movieResult.getResults().size();
    }

    public interface OnMovieClickListener {
        void onMovieClick(int position, ImageView sharedImageView);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding mBinding;

        public MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
