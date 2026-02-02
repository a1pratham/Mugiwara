package com.example.testapp.core.anime;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.testapp.R;
import com.example.testapp.core.anime.adapter.AnimeAdapter;
import com.example.testapp.core.anime.adapter.FeaturedAnimeAdapter;
import com.example.testapp.core.modal.Anime;
import com.example.testapp.core.ui.debug.ClickDebugUtil;

import java.util.ArrayList;
import java.util.List;

public class AnimeFragment extends Fragment {

    private static final long AUTO_SCROLL_DELAY = 4000;

    private Handler sliderHandler;
    private Runnable sliderRunnable;

    private ImageView[] dots;
    private ViewPager2 pager;

    // ================= FRAGMENT =================

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_anime, container, false);

        // ---------- BLUR BACKGROUND (ANDROID 12+) ----------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            View bg = view.findViewById(R.id.bgImage);
            bg.setRenderEffect(
                    android.graphics.RenderEffect.createBlurEffect(
                            40f, 40f, android.graphics.Shader.TileMode.CLAMP
                    )
            );
        }

        // ---------- DEBUG ----------
        ClickDebugUtil.attachToAllClickableViews(view, "ANIME_SCREEN");

        // ================= FEATURED SLIDER =================
        pager = view.findViewById(R.id.featuredPager);
        LinearLayout indicatorLayout = view.findViewById(R.id.featuredIndicator);

        List<Anime> featured = new ArrayList<>();
        featured.add(new Anime("An Adventurer’s Daily Grind", "6.1", "2"));
        featured.add(new Anime("Attack on Titan", "9.0", "25"));
        featured.add(new Anime("Death Note", "8.4", "37"));

        FeaturedAnimeAdapter featuredAdapter =
                new FeaturedAnimeAdapter(featured, this::openAnimeDetail);

        pager.setAdapter(featuredAdapter);
        pager.setOffscreenPageLimit(1);

        setupIndicators(indicatorLayout, featured.size());
        setCurrentIndicator(0);

        // Auto Scroll Handler
        sliderHandler = new Handler(Looper.getMainLooper());

        sliderRunnable = () -> {
            if (pager.getAdapter() == null) return;

            int count = pager.getAdapter().getItemCount();
            if (count <= 1) return;

            int next = pager.getCurrentItem() + 1;
            if (next >= count) next = 0;

            pager.setCurrentItem(next, true);
        };

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentIndicator(position);

                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, AUTO_SCROLL_DELAY);
            }
        });

        // ================= LISTS =================
        RecyclerView recent = view.findViewById(R.id.recentAnimeRecycler);
        RecyclerView popular = view.findViewById(R.id.popularAnimeRecycler);

        setupHorizontalList(recent);
        setupHorizontalList(popular);

        List<Anime> animeList = new ArrayList<>();
        animeList.add(new Anime("Attack on Titan", "9.0", "25"));
        animeList.add(new Anime("Death Note", "8.4", "37"));
        animeList.add(new Anime("Jujutsu Kaisen", "8.6", "24"));
        animeList.add(new Anime("Bleach", "7.9", "366"));

        AnimeAdapter adapter = new AnimeAdapter(animeList, this::openAnimeDetail);

        recent.setAdapter(adapter);
        popular.setAdapter(adapter);

        return view;
    }

    // ================= NAVIGATION =================

    private void openAnimeDetail(Anime anime) {

        // ✅ Works ONLY if AnimeDetailFragment has newInstance()
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

    // ================= LIFECYCLE =================

    @Override
    public void onResume() {
        super.onResume();

        if (sliderHandler != null) {
            sliderHandler.postDelayed(sliderRunnable, AUTO_SCROLL_DELAY);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (sliderHandler != null) {
            sliderHandler.removeCallbacks(sliderRunnable);
        }
    }

    // ================= HELPERS =================

    private void setupIndicators(LinearLayout layout, int count) {

        dots = new ImageView[count];
        layout.removeAllViews();

        for (int i = 0; i < count; i++) {

            ImageView dot = new ImageView(requireContext());
            dot.setImageResource(R.drawable.dot_inactive);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

            params.setMargins(6, 0, 6, 0);
            dot.setLayoutParams(params);

            layout.addView(dot);
            dots[i] = dot;
        }
    }

    private void setCurrentIndicator(int index) {

        if (dots == null) return;

        for (int i = 0; i < dots.length; i++) {
            dots[i].setImageResource(
                    i == index ? R.drawable.dot_active : R.drawable.dot_inactive
            );
        }
    }

    private void setupHorizontalList(RecyclerView recyclerView) {

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        // ✅ Prevent duplicate decorations
        if (recyclerView.getItemDecorationCount() > 0) return;

        int startSpacing = dpToPx(16);
        int middleSpacing = dpToPx(8);
        int endSpacing = dpToPx(4);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(
                    @NonNull Rect outRect,
                    @NonNull View view,
                    @NonNull RecyclerView parent,
                    @NonNull RecyclerView.State state
            ) {

                int position = parent.getChildAdapterPosition(view);
                int itemCount = state.getItemCount();

                if (position == 0) {
                    outRect.left = startSpacing;
                    outRect.right = middleSpacing / 2;
                } else if (position == itemCount - 1) {
                    outRect.left = middleSpacing / 2;
                    outRect.right = endSpacing;
                } else {
                    outRect.left = middleSpacing / 2;
                    outRect.right = middleSpacing / 2;
                }
            }
        });
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
