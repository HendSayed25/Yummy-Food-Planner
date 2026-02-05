package com.example.yummy_food_planner.presentation.authentication.signin.view;

public interface SignInView {
    void onSignInSuccess();
    void showError(String message, SignInErrorType type);
    void onNoInternet();
}