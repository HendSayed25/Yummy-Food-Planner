package com.example.yummy_food_planner.presentation.authentication.signin.view;

public interface SignInView {
    void onSignInSuccess();

    void showError(int messageId, SignInErrorType type);

    void onNoInternet();
}