package com.example.testapp.core.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.ViewCompat;

import com.example.testapp.R;
import com.example.testapp.core.anime.AnimeFragment;
import com.example.testapp.core.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ ENABLE EDGE-TO-EDGE (THIS REMOVES BLUE STRIP)
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        setContentView(R.layout.activity_main);

        // ✅ HANDLE STATUS BAR INSETS FOR TOP CONTENT
        View root = findViewById(R.id.fragment_container);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            int topInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            v.setPadding(0, topInset, 0, 0);
            return insets;
        });

        // Default screen
        loadFragment(new HomeFragment());
        highlight(findViewById(R.id.nav_home));

        findViewById(R.id.nav_anime).setOnClickListener(v -> {
            highlight(v);
            loadFragment(new AnimeFragment());
        });

        findViewById(R.id.nav_home).setOnClickListener(v -> {
            highlight(v);
            loadFragment(new HomeFragment());
        });

        findViewById(R.id.nav_manga).setOnClickListener(v -> {
            highlight(v);
            // TODO MangaFragment
        });
    }

    private void loadFragment(androidx.fragment.app.Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void highlight(View selected) {
        findViewById(R.id.nav_anime).setAlpha(0.5f);
        findViewById(R.id.nav_home).setAlpha(0.5f);
        findViewById(R.id.nav_manga).setAlpha(0.5f);
        selected.setAlpha(1f);
    }
}
