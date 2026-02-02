package com.example.testapp.core.anime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ListEditorBottomSheet extends BottomSheetDialogFragment {

    private TextView statusSelector;
    private PopupWindow popupWindow;

    // ✅ Listener to send status back
    public interface OnStatusSelectedListener {
        void onStatusSelected(String status);
    }

    private OnStatusSelectedListener listener;

    public void setOnStatusSelectedListener(OnStatusSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.bottom_sheet_list_editor, container, false);

        // ✅ Selector pill
        statusSelector = view.findViewById(R.id.status_selector);

        // ✅ Click opens floating dropdown
        statusSelector.setOnClickListener(v -> showDropdown());

        return view;
    }

    // ==============================
    // ✅ Floating Dropdown Popup
    // ==============================
    private void showDropdown() {

        // Close old popup if open
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        // Inflate dropdown layout
        View popupView = LayoutInflater.from(getContext())
                .inflate(R.layout.dropdown_status, null);

        popupWindow = new PopupWindow(
                popupView,
                statusSelector.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // ✅ Bind each option
        bindPopupOption(popupView, R.id.optionPlanning, "PLANNING");
        bindPopupOption(popupView, R.id.optionWatching, "WATCHING");
        bindPopupOption(popupView, R.id.optionCompleted, "COMPLETED");
        bindPopupOption(popupView, R.id.optionRewatching, "RE-WATCHING");
        bindPopupOption(popupView, R.id.optionPaused, "PAUSED");
        bindPopupOption(popupView, R.id.optionDropped, "DROPPED");

        // ✅ Show below selector
        popupWindow.showAsDropDown(statusSelector, 0, 12);
    }

    // ==============================
    // ✅ Option Click Handler
    // ==============================
    private void bindPopupOption(View popupView, int id, String value) {

        TextView option = popupView.findViewById(id);

        option.setOnClickListener(v -> {

            // Update selector text
            statusSelector.setText(value);

            // Send back to fragment
            if (listener != null) {
                listener.onStatusSelected(value);
            }

            // Close popup + sheet
            popupWindow.dismiss();
            dismiss();
        });
    }

    // ==============================
    // ✅ Cleanup
    // ==============================
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}
