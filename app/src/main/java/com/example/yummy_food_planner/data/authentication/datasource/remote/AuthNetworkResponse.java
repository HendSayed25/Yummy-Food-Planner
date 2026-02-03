package com.example.yummy_food_planner.data.authentication.datasource.remote;

public interface AuthNetworkResponse {
    void onSuccess(String userId);
    void onError(Exception exception);
}