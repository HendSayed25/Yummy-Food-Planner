package com.example.yummy_food_planner.presentation.authentication.signup.view;

public interface SignupView {
    void showError(String message,SignupErrorType type);
    void onSignupSuccess(String userId);
    void noInternet();
    void showViews();
}