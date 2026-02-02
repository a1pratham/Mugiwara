package com.example.testapp.core.anime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.core.modal.RecommendedAnime;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    // ✅ Click Listener Interface
    public interface OnRecommendedClickListener {
        void onRecommendedClick(RecommendedAnime anime);
    }

    private final List<RecommendedAnime> recommendedList;
    private final OnRecommendedClickListener listener;

    public RecommendedAdapter(
            List<RecommendedAnime> recommendedList,
            OnRecommendedClickListener listener
    ) {
        this.recommendedList = recommendedList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommended, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RecommendedAnime anime = recommendedList.get(position);

        holder.txtTitle.setText(anime.title);
        holder.imgPoster.setImageResource(anime.imageResId);

        // ✅ Click Event
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecommendedClick(anime);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    // ================= ViewHolder =================
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgPoster;
        TextView txtTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.imgRecommendedPoster);
            txtTitle = itemView.findViewById(R.id.txtRecommendedTitle);
        }
    }
}
