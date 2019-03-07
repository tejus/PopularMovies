package com.tejus.popularmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.databinding.ItemMovieBinding;
import com.tejus.popularmovies.db.MovieDatabase;
import com.tejus.popularmovies.model.Movie;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MovieViewHolder> {

    private Context mContext;
    private String mSortMode;
    private final OnMovieClickListener mClickHandler;

    public MainAdapter(Context context, String sortMode, OnMovieClickListener clickHandler) {
        mContext = context;
        mSortMode = sortMode;
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
        Movie movie = MovieDatabase.getMovie(mContext, mSortMode, i);
        movieViewHolder.mBinding.setMovie(movie);
        movieViewHolder.mBinding.getRoot().setOnClickListener((View v) -> {
            mClickHandler.onMovieClick(movieViewHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        if (MovieDatabase.getMovies(mContext, mSortMode) == null) {
            return 0;
        } else
            return MovieDatabase.getMovies(mContext, mSortMode).getResults().size();
    }

    public interface OnMovieClickListener {
        void onMovieClick(int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding mBinding;

        public MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
