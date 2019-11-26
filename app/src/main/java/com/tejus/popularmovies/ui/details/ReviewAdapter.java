package com.tejus.popularmovies.ui.details;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tejus.popularmovies.databinding.ItemReviewBinding;
import com.tejus.popularmovies.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> mReviews;

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
    }

    @Override
    public int getItemCount() {
        if (mReviews == null)
            return 0;
        else
            return mReviews.size();
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        ItemReviewBinding mBinding;

        public ReviewViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
