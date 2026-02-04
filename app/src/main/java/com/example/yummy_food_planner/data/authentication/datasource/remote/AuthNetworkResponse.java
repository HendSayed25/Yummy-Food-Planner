package com.example.yummy_food_planner.data.authentication.datasource.remote;

import com.example.yummy_food_planner.data.authentication.model.User;

public interface AuthNetworkResponse {
    void onSuccess(User user);
    void onError(Exception exception);
}