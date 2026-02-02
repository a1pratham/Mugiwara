package com.example.testapp.core.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.core.home.adapter.MediaCardAdapter;
import com.example.testapp.R;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView continueWatching =
                view.findViewById(R.id.continueWatchingRecycler);
        RecyclerView continueReading =
                view.findViewById(R.id.continueReadingRecycler);
        RecyclerView recommended =
                view.findViewById(R.id.recommendedRecycler);

        continueWatching.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));

        continueReading.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));

        recommended.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));

        List<String> dummy = Arrays.asList(
                "One Piece",
                "My Hero Academia",
                "Bleach",
                "Jujutsu Kaisen",
                "Berserk"
        );

        MediaCardAdapter adapter = new MediaCardAdapter(dummy);

        continueWatching.setAdapter(adapter);
        continueReading.setAdapter(adapter);
        recommended.setAdapter(adapter);

        return view;
    }
}
