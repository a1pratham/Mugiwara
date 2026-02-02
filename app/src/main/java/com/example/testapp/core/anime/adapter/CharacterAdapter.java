package com.example.testapp.core.anime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.core.modal.Character;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private final List<Character> characterList;

    public CharacterAdapter(List<Character> characterList) {
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Character character = characterList.get(position);

        holder.txtName.setText(character.name);
        holder.txtRole.setText(character.role);
        holder.imgCharacter.setImageResource(character.imageResId);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    // ================= ViewHolder =================
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCharacter;
        TextView txtName, txtRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCharacter = itemView.findViewById(R.id.imgCharacter);
            txtName = itemView.findViewById(R.id.txtCharacterName);
            txtRole = itemView.findViewById(R.id.txtCharacterRole);
        }
    }
}
