package com.example.yummy_food_planner.presentation.profile.view;

public interface ProfileView {
    void showUserData(String userName,String userEmail);
    void showError(int messageId);
    void userIsGuest();
    void userNotAGuest();
    void syncDataSuccessfully();
    void noInternet();
    void logOut();
}