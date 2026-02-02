package com.example.testapp.core.anime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.core.modal.Anime;

import java.util.List;

public class FeaturedAnimeAdapter
        extends RecyclerView.Adapter<FeaturedAnimeAdapter.HeroViewHolder> {

    public interface OnAnimeClick {
        void onAnimeClick(Anime anime);
    }

    private final List<Anime> list;
    private final OnAnimeClick listener;

    public FeaturedAnimeAdapter(List<Anime> list, OnAnimeClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_anime, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull HeroViewHolder holder,
            int position
    ) {
        Anime anime = list.get(position);

        holder.title.setText(anime.title);
        holder.status.setText("RELEASING");
        holder.episodes.setText(anime.episodes);
        holder.genre.setText("Sci-Fi · Thriller");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAnimeClick(anime);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HeroViewHolder extends RecyclerView.ViewHolder {

        ImageView bg, poster;
        TextView title, status, episodes, genre;

        HeroViewHolder(@NonNull View itemView) {
            super(itemView);

            bg = itemView.findViewById(R.id.heroBg);
            poster = itemView.findViewById(R.id.heroPoster);
            title = itemView.findViewById(R.id.heroTitle);
            status = itemView.findViewById(R.id.heroStatus);
            episodes = itemView.findViewById(R.id.heroEpisodes);
            genre = itemView.findViewById(R.id.heroGenre);
        }
    }
}
