package com.example.yummy_food_planner.onboarding;

import android.os.Bundle;
import android.os.Handler;
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
import androidx.navigation.Navigation;

import com.example.yummy_food_planner.R;


public class OnboardingFragment3 extends BaseOnboardingFragment {

    private AppCompatButton startBtn, backBtn;
    private LinearLayout dotContainer;
    private ImageView onboardingImage;
    private TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startBtn = view.findViewById(R.id.startBtn);
        dotContainer = view.findViewById(R.id.dotsContainer3);
        onboardingImage = view.findViewById(R.id.onboardingImage3);
        description = view.findViewById(R.id.descriptionTv3);
        backBtn = view.findViewById(R.id.backBtn3);

        setupDots(dotContainer);
        applySpecialAnimations();

        startBtn.setOnClickListener(v -> {
            //navigate to login screen or home
        });

        backBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }

    @Override
    protected int getCurrentPage() {
        return 2;
    }

    @Override
    protected int getTotalPages() {
        return 3;
    }

    private void applySpecialAnimations() {

        Animation slideInTop = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_slide_in_top);
        onboardingImage.startAnimation(slideInTop);

        Animation slideUpFadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_fade_in);
        slideUpFadeIn.setStartOffset(600);
        description.startAnimation(slideUpFadeIn);

        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.onboarding_fade_in);
        fadeIn.setStartOffset(700);
        dotContainer.startAnimation(fadeIn);

        Animation zoomInRotate = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in_rotate);
        zoomInRotate.setStartOffset(900);
        startBtn.startAnimation(zoomInRotate);

        backBtn.startAnimation(zoomInRotate);

        new Handler().postDelayed(() -> {
            Animation pulse = AnimationUtils.loadAnimation(requireContext(), R.anim.pluse);
            startBtn.startAnimation(pulse);
        }, 1500);
    }
}