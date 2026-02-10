package com.example.yummy_food_planner.presentation.welcome.onboarding.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yummy_food_planner.R;


public class OnboardingFragment1 extends BaseOnboardingFragment {

    private AppCompatButton nextBtn, skipBtn;
    private LinearLayout dotContainer;
    private ImageView onboardingImage;
    private TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextBtn = view.findViewById(R.id.nextBtn1);
        skipBtn = view.findViewById(R.id.skipBtn1);
        dotContainer = view.findViewById(R.id.dotsContainer1);
        onboardingImage = view.findViewById(R.id.onboardingImage1);
        description = view.findViewById(R.id.descriptionTv1);


        setupDots(dotContainer);
        applyAnimations();

        nextBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_onboardingFragment1_to_onboardingFragment2);

        });

        skipBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_onboardingFragment1_to_onboardingFragment3);
        });
    }

    @Override
    protected int getCurrentPage() {
        return 0;
    }

    @Override
    protected int getTotalPages() {
        return 3;
    }

    private void applyAnimations() {
        Animation slideIn = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_slid_in_left);
        onboardingImage.startAnimation(slideIn);

        Animation slidInTop = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_slide_in_top);
        skipBtn.startAnimation(slidInTop);

        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_fade_in);
        fadeIn.setStartOffset(300);
        description.startAnimation(fadeIn);
    }
}