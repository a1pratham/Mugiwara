package com.example.testapp.core.anime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.core.modal.Anime;
import com.example.testapp.ui.debug.ClickDebug;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

    public interface OnAnimeClickListener {
        void onAnimeClick(Anime anime);
    }

    private final List<Anime> list;
    private final OnAnimeClickListener listener;

    public AnimeAdapter(List<Anime> list, OnAnimeClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anime_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anime anime = list.get(position);

        holder.title.setText(anime.title);
        holder.rating.setText(anime.rating);
        holder.episodes.setText(anime.episodes);

        ClickDebug.attach(holder.itemView, "CARD: " + anime.title);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onAnimeClick(anime);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, rating, episodes;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            episodes = itemView.findViewById(R.id.episodes);

        }

    }
}
