package com.example.yummy_food_planner.presentation.profile.presenter;

public interface ProfilePresenter {
    void logOut();
    void getUserData();
    void isUserGuest();
    void onDestroy();
}