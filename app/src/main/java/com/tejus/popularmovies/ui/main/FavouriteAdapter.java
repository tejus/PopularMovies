package com.tejus.popularmovies.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.databinding.ItemMovieBinding;
import com.tejus.popularmovies.model.Movie;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private List<Movie> mMovies;
    private final OnFavouriteClickListener mClickListener;

    public FavouriteAdapter(OnFavouriteClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemMovieBinding binding = ItemMovieBinding.inflate(inflater, viewGroup, false);
        return new FavouriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder favouriteViewHolder, int i) {
        favouriteViewHolder.mBinding.setMovie(mMovies.get(i));
        favouriteViewHolder.mBinding.getRoot().setOnClickListener((View v) -> {
            mClickListener.onFavouriteClick(favouriteViewHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        } else {
            return mMovies.size();
        }
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public Movie getMovieAtPos(int pos) {
        return mMovies.get(pos);
    }

    public interface OnFavouriteClickListener {
        void onFavouriteClick(int position);
    }

    public class FavouriteViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding mBinding;

        public FavouriteViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
