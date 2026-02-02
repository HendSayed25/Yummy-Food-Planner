package com.example.yummy_food_planner.presentation.authentication.signup.view;

public interface SignupView {
    void showError(String message);
    void onSignupSuccess(String userId);
    void noInternet();
    void showViews();
}
