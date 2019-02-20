package com.tejus.popularmovies.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tejus.popularmovies.R;
import com.tejus.popularmovies.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MovieViewHolder> {

    private static final String LOG_TAG = MainAdapter.class.getSimpleName();

    private final OnMovieClickListener mClickHandler;
    private List<Movie> movies;

    public MainAdapter(OnMovieClickListener clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Picasso.get()
                .load(movies.get(i).getPosterPathUri())
                .placeholder(R.color.colorPrimaryDark)
                .error(R.color.colorPrimaryDark)
                .into(movieViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (movies == null)
            return 0;
        else
            return movies.size();
    }

    public interface OnMovieClickListener {
        void onItemClick(int position);
    }

    public void setMovies(List<Movie> movies) {
        Log.d(LOG_TAG, "Setting received movies to the MovieResult instance and notifying data change");
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_poster_thumb)
        ImageView mImageView;

        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onItemClick(getAdapterPosition());
        }
    }
}
