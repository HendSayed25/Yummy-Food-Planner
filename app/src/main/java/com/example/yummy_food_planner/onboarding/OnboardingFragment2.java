package com.example.yummy_food_planner.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.yummy_food_planner.R;


public class OnboardingFragment2 extends BaseOnboardingFragment {
    private AppCompatButton nextBtn, skipBtn, backBtn;
    private LinearLayout dotContainer;
    private ImageView onboardingImage;
    private TextView description;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextBtn = view.findViewById(R.id.nextBtn2);
        skipBtn = view.findViewById(R.id.skipBtn2);
        backBtn = view.findViewById(R.id.backBtn2);
        dotContainer = view.findViewById(R.id.dotsContainer2);
        onboardingImage = view.findViewById(R.id.onboardingImage2);
        description = view.findViewById(R.id.descriptionTv2);

        setupDots(dotContainer);
        applyAnimations();

        nextBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_onboardingFragment2_to_onboardingFragment3);
        });

        skipBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_onboardingFragment2_to_onboardingFragment3);
        });

        backBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }

    @Override
    protected int getCurrentPage() {
        return 1;
    }

    @Override
    protected int getTotalPages() {
        return 3;
    }

    private void applyAnimations() {
        Animation slideIn = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_slide_in_right);
        onboardingImage.startAnimation(slideIn);

        nextBtn.startAnimation(slideIn);

        Animation slidInTop = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_slide_in_top);
        skipBtn.startAnimation(slidInTop);

        Animation slidInLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_slid_in_left);
        backBtn.startAnimation(slidInLeft);

        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_fade_in);
        fadeIn.setStartOffset(300);
        description.startAnimation(fadeIn);
    }
}