package com.example.testapp.core.ui.debug;

import android.view.View;
import android.view.ViewGroup;

import com.example.testapp.ui.debug.ClickDebug; // ✅ THIS LINE FIXES IT

public class ClickDebugUtil {

    public static void attachToAllClickableViews(View root, String prefix) {
        if (root == null) return;

        if (root.isClickable()) {
            String name = root.getId() != View.NO_ID
                    ? root.getResources().getResourceEntryName(root.getId())
                    : root.getClass().getSimpleName();

            ClickDebug.attach(root, prefix + ": " + name);
        }

        if (root instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) root;
            for (int i = 0; i < group.getChildCount(); i++) {
                attachToAllClickableViews(group.getChildAt(i), prefix);
            }
        }
    }
}
