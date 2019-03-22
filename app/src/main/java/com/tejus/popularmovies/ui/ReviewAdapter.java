package com.tejus.popularmovies.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tejus.popularmovies.databinding.ItemReviewBinding;
import com.tejus.popularmovies.model.Reviews;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final OnReviewClickListener mClickListener;
    private List<Reviews> mReviews;

    public ReviewAdapter(OnReviewClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemReviewBinding binding = ItemReviewBinding.inflate(inflater, viewGroup, false);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        reviewViewHolder.mBinding.setReview(mReviews.get(i));
        reviewViewHolder.mBinding.getRoot().setOnClickListener((View v) -> {
            mClickListener.onReviewClick(reviewViewHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        if (mReviews == null)
            return 0;
        else
            return mReviews.size();
    }

    public void setReviews(List<Reviews> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    public interface OnReviewClickListener {
        void onReviewClick(int position);
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        ItemReviewBinding mBinding;

        public ReviewViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
