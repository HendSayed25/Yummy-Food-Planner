package com.example.yummy_food_planner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        startEnterAnimation();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startEndAnimation();
            }
        }, 2500);
    }

    private void init() {
        logo = findViewById(R.id.logo_ic);
        appName = findViewById(R.id.appName);
    }

    private void startEnterAnimation() {
        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        Animation textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_anim);

        logo.startAnimation(logoAnimation);
        appName.startAnimation(textAnimation);
    }

    private void startEndAnimation() {
        Animation fadeOut = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.rotate_fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.GONE);
                appName.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        logo.startAnimation(fadeOut);
        appName.startAnimation(fadeOut);
    }
}