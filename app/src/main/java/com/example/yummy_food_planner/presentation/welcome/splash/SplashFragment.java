package com.example.yummy_food_planner.presentation.welcome.splash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yummy_food_planner.R;

public class SplashFragment extends Fragment {

    private ImageView logo;
    private TextView appName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logo = view.findViewById(R.id.logo_ic);
        appName = view.findViewById(R.id.appName);

        startEnterAnimation();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startEndAnimation(view);
            }
        }, 2500);
    }

    private void startEnterAnimation() {
        Animation logoAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.logo_anim);
        Animation textAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.text_anim);

        logo.startAnimation(logoAnimation);
        appName.startAnimation(textAnimation);
    }

    private void startEndAnimation(View view) {
        Animation fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_fade_out);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.GONE);
                appName.setVisibility(View.GONE);

                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_onboardingFragment1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        logo.startAnimation(fadeOut);
        appName.startAnimation(fadeOut);
    }
}