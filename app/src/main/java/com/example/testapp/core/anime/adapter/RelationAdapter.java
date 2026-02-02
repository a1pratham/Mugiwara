package com.example.testapp.core.anime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.core.modal.Relation;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class RelationAdapter extends RecyclerView.Adapter<RelationAdapter.ViewHolder> {

    // ✅ Click Listener Interface
    public interface OnRelationClickListener {
        void onRelationClick(Relation relation);
    }

    private final List<Relation> relationList;
    private final OnRelationClickListener listener;

    public RelationAdapter(
            List<Relation> relationList,
            OnRelationClickListener listener
    ) {
        this.relationList = relationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_relation, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Relation relation = relationList.get(position);

        holder.txtTitle.setText(relation.title);
        holder.txtType.setText(relation.type.toUpperCase());
        holder.imgPoster.setImageResource(relation.imageResId);

        // ✅ Click Event
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRelationClick(relation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return relationList.size();
    }

    // ================= ViewHolder =================
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView imgPoster;
        TextView txtTitle, txtType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.imgRelationPoster);
            txtTitle = itemView.findViewById(R.id.txtRelationTitle);
            txtType = itemView.findViewById(R.id.txtRelationType);
        }
    }
}
