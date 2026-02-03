package com.example.yummy_food_planner.data.authentication.datasource.remote;

public interface AuthNetworkResponse {
    void onSignupSuccess(String userId);
    void onError(Exception exception);
}