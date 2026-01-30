package com.example.yummy_food_planner.welcome.onboarding;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.yummy_food_planner.R;
import com.google.android.material.transition.MaterialSharedAxis;

public abstract class BaseOnboardingFragment extends Fragment {

    protected abstract int getCurrentPage();

    protected abstract int getTotalPages();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MaterialSharedAxis enterTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
        MaterialSharedAxis exitTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
        MaterialSharedAxis returnTransition = new MaterialSharedAxis(MaterialSharedAxis.X, false);

        setEnterTransition(enterTransition);
        setExitTransition(exitTransition);
        setReturnTransition(returnTransition);
    }

    protected void setupDots(LinearLayout dotsContainer) {
        dotsContainer.removeAllViews();

        int totalPages = getTotalPages();
        int currentPage = getCurrentPage();

        for (int i = 0; i < totalPages; i++) {
            View dot = new View(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(12), dpToPx(12));
            params.setMargins(dpToPx(4), 0, dpToPx(4), 0);

            if (i == currentPage) {
                params.width = dpToPx(32);
                params.height = dpToPx(12);
                dot.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.dot_active));
            } else {
                dot.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.dot_inactive));
            }

            dot.setLayoutParams(params);
            dotsContainer.addView(dot);
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}