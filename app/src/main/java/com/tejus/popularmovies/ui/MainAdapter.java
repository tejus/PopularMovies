package com.tejus.popularmovies.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tejus.popularmovies.R;
import com.tejus.popularmovies.model.MovieDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MovieViewHolder> {

    private final OnMovieClickListener mClickHandler;

    public MainAdapter(OnMovieClickListener clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup, false);
        // TODO (8) Declare and initialise a data binding field

        //  TODO (12) Pass the data binding field instead of the view to the ViewHolder
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        //  TODO (16) Obtain the movie object at position i

        //  TODO (17) Use the data binding object to bind the movie instead of directly
        //  using Picasso here.
        Picasso.get()
                .load(MovieDatabase.movieResult.getResults().get(i).getPosterPath())
                .placeholder(R.color.colorPrimaryDark)
                .error(R.color.colorPrimaryDark)
                .into(movieViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (MovieDatabase.movieResult == null) {
            return 0;
        } else
            return MovieDatabase.movieResult.getResults().size();
    }

    public interface OnMovieClickListener {
        void onItemClick(int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // TODO (11) Declare a data binding field

        // TODO (15) Replace occurrences of the ImageView with the data binding field
        @BindView(R.id.iv_poster_thumb)
        ImageView mImageView;

        //  TODO (14) Receive the data binding field instead of the view
        public MovieViewHolder(View view) {
            super(view);
            //  TODO (13) Initialise the data binding field instead of Butterknife.bind()
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onItemClick(getAdapterPosition());
        }
    }
}
