package com.example.yummy_food_planner.presentation.welcome.splash.view;

public interface SplashView {
    void goToOnboardingScreens();
    void goToHomeScreen();
    void goToSignInScreen();
    void showError(int messageId);
}