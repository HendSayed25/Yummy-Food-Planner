package com.example.yummy_food_planner.presentation.authentication.signup.presenter;

public interface SignupPresenter {
    void signup(String email, String password, String confirmedPassword);
    void onDestroy();
}