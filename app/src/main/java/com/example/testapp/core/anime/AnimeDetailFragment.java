package com.example.testapp.core.anime;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapp.R;
import com.example.testapp.core.anime.adapter.CharacterAdapter;
import com.example.testapp.core.anime.adapter.RelationAdapter;
import com.example.testapp.core.anime.adapter.RecommendedAdapter;
import com.example.testapp.core.modal.Character;
import com.example.testapp.core.modal.Relation;
import com.example.testapp.core.modal.RecommendedAnime;
import com.google.android.flexbox.FlexboxLayout;

import java.util.Arrays;
import java.util.List;

public class AnimeDetailFragment extends Fragment {

    // ================= UI =================
    private LinearLayout watchActionsRow;
    private TextView statusPill;
    private TextView tabInfo, tabWatch;

    // Synopsis Expand
    private TextView tvSynopsisText;
    private TextView btnReadMore;
    private boolean isSynopsisExpanded = false;

    private String currentStatus = "PLANNING";

    // Sections
    private FlexboxLayout genreContainer;
    private RecyclerView rvCharacters, rvRelations, rvRecommended;

    // ==============================
    // ✅ newInstance()
    // ==============================
    public static AnimeDetailFragment newInstance(
            String title,
            String rating,
            String episodes
    ) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("rating", rating);
        args.putString("episodes", episodes);

        AnimeDetailFragment fragment = new AnimeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ================= MAIN VIEW =================
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_anime_detail, container, false);

        // ================= BLUR BACKGROUND =================
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {

            View bg = view.findViewById(R.id.bgImage);

            if (bg != null) {
                bg.setRenderEffect(
                        android.graphics.RenderEffect.createBlurEffect(
                                1f,
                                1f,
                                android.graphics.Shader.TileMode.CLAMP
                        )
                );
            }
        }

        // ================= Title + Rating =================
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtRating = view.findViewById(R.id.txtRating);

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String rating = getArguments().getString("rating");
            String episodes = getArguments().getString("episodes");

            txtTitle.setText(title);
            txtRating.setText("⭐ " + rating + " • " + episodes + " Episodes");
        }

        // ================= Information Table =================
        LinearLayout infoContainer = view.findViewById(R.id.infoContainer);

        addInfoRow(infoContainer, "Mean Score", "8.5 / 10", true);
        addInfoRow(infoContainer, "Status", "FINISHED", false);
        addInfoRow(infoContainer, "Total Episodes", "25", false);
        addInfoRow(infoContainer, "Average Duration", "24 min", false);
        addInfoRow(infoContainer, "Format", "TV", false);
        addInfoRow(infoContainer, "Source", "MANGA", true);
        addInfoRow(infoContainer, "Studio", "WIT STUDIO", true);
        addInfoRow(infoContainer, "Author", "Hajime Isayama", true);
        addInfoRow(infoContainer, "Season", "SPRING 2013", true);
        addInfoRow(infoContainer, "Start Date", "7 April, 2013", false);
        addInfoRow(infoContainer, "End Date", "28 September, 2013", false);

        // ================= Tabs =================
        tabInfo = view.findViewById(R.id.tabInfo);
        tabWatch = view.findViewById(R.id.tabWatch);

        // ================= Status + Watch Row =================
        watchActionsRow = view.findViewById(R.id.watchActionsRow);
        statusPill = view.findViewById(R.id.statusPill);

        // ================= Synopsis Expand =================
        tvSynopsisText = view.findViewById(R.id.tvSynopsisText);
        btnReadMore = view.findViewById(R.id.btnReadMore);

        tvSynopsisText.setText(
                "Gold Roger was known as the Pirate King... \n\n" +
                        "Enter Monkey D. Luffy, a 17-year-old boy who dreams of finding One Piece..."
        );

        // Expand / Collapse
        if (btnReadMore != null) {
            btnReadMore.setOnClickListener(v -> toggleSynopsis());
        }

        // Tap synopsis also expands
        tvSynopsisText.setOnClickListener(v -> {
            if (btnReadMore != null) btnReadMore.performClick();
        });

        // Hide button if synopsis is short
        tvSynopsisText.post(() -> {
            if (tvSynopsisText.getLayout() != null) {
                int lines = tvSynopsisText.getLayout().getLineCount();
                if (lines <= 4) {
                    btnReadMore.setVisibility(View.GONE);
                }
            }
        });

        // ================= Genre Chips =================
        genreContainer = view.findViewById(R.id.genreContainer);

        String[] genreList = {"Action", "Adventure", "Drama", "Fantasy"};

        for (String g : genreList) {
            View chipView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_genre_chip, genreContainer, false);

            TextView chip = chipView.findViewById(R.id.chipGenre);
            chip.setText(g);

            genreContainer.addView(chipView);
        }

        // ================= RecyclerViews =================
        rvCharacters = view.findViewById(R.id.rvCharacters);
        rvRelations = view.findViewById(R.id.rvRelations);
        rvRecommended = view.findViewById(R.id.rvRecommended);

        // ================= Characters =================
        List<Character> characterCards = Arrays.asList(
                new Character("Luffy", "Main", R.drawable.temp_poster),
                new Character("Zoro", "Supporting", R.drawable.temp_poster),
                new Character("Nami", "Supporting", R.drawable.temp_poster),
                new Character("Sanji", "Supporting", R.drawable.temp_poster)
        );

        rvCharacters.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        rvCharacters.setAdapter(new CharacterAdapter(characterCards));

        // ================= Relations =================
        List<Relation> relationCards = Arrays.asList(
                new Relation("Attack on Titan Season 2", "Sequel", "9.0", "12", R.drawable.temp_poster),
                new Relation("Attack on Titan Junior High", "Spin-off", "7.5", "12", R.drawable.temp_poster)
        );

        rvRelations.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        rvRelations.setAdapter(
                new RelationAdapter(relationCards, this::openRelationDetail)
        );

        // ================= Recommended =================
        List<RecommendedAnime> recommendedCards = Arrays.asList(
                new RecommendedAnime("Naruto Shippuden", "8.5", "500", R.drawable.temp_poster),
                new RecommendedAnime("Bleach TYBW", "9.1", "52", R.drawable.temp_poster),
                new RecommendedAnime("One Piece", "9.0", "1100+", R.drawable.temp_poster)
        );

        rvRecommended.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        rvRecommended.setAdapter(
                new RecommendedAdapter(recommendedCards, this::openAnimeDetail)
        );

        // ================= Status Pill =================
        updateStatusPill();

        if (statusPill != null) {
            statusPill.setOnClickListener(v -> {
                ListEditorBottomSheet sheet = new ListEditorBottomSheet();

                sheet.setOnStatusSelectedListener(selectedStatus -> {
                    currentStatus = selectedStatus;
                    updateStatusPill();
                });

                sheet.show(getParentFragmentManager(), "list_editor");
            });
        }

        // ================= Default Tab =================
        showInfoTab();

        tabInfo.setOnClickListener(v -> showInfoTab());
        tabWatch.setOnClickListener(v -> showWatchTab());

        return view;
    }

    // ================= Toggle Synopsis =================
    private void toggleSynopsis() {

        if (isSynopsisExpanded) {
            tvSynopsisText.setMaxLines(4);
            tvSynopsisText.setEllipsize(TextUtils.TruncateAt.END);
            btnReadMore.setText("Read More");
            isSynopsisExpanded = false;

        } else {
            tvSynopsisText.setMaxLines(Integer.MAX_VALUE);
            tvSynopsisText.setEllipsize(null);
            btnReadMore.setText("Show Less");
            isSynopsisExpanded = true;
        }

        tvSynopsisText.requestLayout();
    }

    // ================= Update Pill =================
    private void updateStatusPill() {
        if (statusPill != null) {
            statusPill.setText(currentStatus + " ▼");
        }
    }

    // ================= INFO TAB =================
    private void showInfoTab() {

        if (watchActionsRow != null)
            watchActionsRow.setVisibility(View.GONE);

        if (statusPill != null)
            statusPill.setVisibility(View.VISIBLE);

        tabInfo.setTextColor(getResources().getColor(android.R.color.white));
        tabWatch.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }

    // ================= WATCH TAB =================
    private void showWatchTab() {

        if (watchActionsRow != null)
            watchActionsRow.setVisibility(View.VISIBLE);

        if (statusPill != null)
            statusPill.setVisibility(View.GONE);

        tabWatch.setTextColor(getResources().getColor(android.R.color.white));
        tabInfo.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }

    // ================= Open Recommended Detail =================
    private void openAnimeDetail(RecommendedAnime anime) {

        AnimeDetailFragment fragment = AnimeDetailFragment.newInstance(
                anime.title,
                anime.rating,
                anime.episodes
        );

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // ================= Open Relation Detail =================
    private void openRelationDetail(Relation relation) {

        AnimeDetailFragment fragment = AnimeDetailFragment.newInstance(
                relation.title,
                relation.rating,
                relation.episodes
        );

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // ================= Add Info Row =================
    private void addInfoRow(
            LinearLayout container,
            String label,
            String value,
            boolean highlight
    ) {

        View row = LayoutInflater.from(getContext())
                .inflate(R.layout.item_info_row, container, false);

        TextView txtLabel = row.findViewById(R.id.txtLabel);
        TextView txtValue = row.findViewById(R.id.txtValue);

        txtLabel.setText(label);
        txtValue.setText(value);

        if (highlight) {
            txtValue.setTextColor(0xFFFF4D8D);
        }

        container.addView(row);
    }
}
