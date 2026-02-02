package com.example.testapp.ui.debug;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class ClickDebug {

    public static void attach(View view, String name) {
        view.setOnClickListener(v -> {
            Toast.makeText(
                    v.getContext(),
                    name + " CLICKED",
                    Toast.LENGTH_SHORT
            ).show();

            // temporary glow feedback
            v.animate()
                    .scaleX(0.97f)
                    .scaleY(0.97f)
                    .setDuration(80)
                    .withEndAction(() ->
                            v.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(80)
                                    .start()
                    )
                    .start();
        });
    }
}
