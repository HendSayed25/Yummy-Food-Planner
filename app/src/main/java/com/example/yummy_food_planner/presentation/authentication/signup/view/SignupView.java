package com.example.yummy_food_planner.presentation.authentication.signup.view;

public interface SignupView {
    void showError(int messageId, SignupErrorType type);
    void onSignupSuccess();
    void noInternet();
}