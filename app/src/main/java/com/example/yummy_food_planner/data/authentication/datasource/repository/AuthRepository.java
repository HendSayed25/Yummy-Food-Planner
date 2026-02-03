package com.example.yummy_food_planner.data.authentication.datasource.repository;

import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthNetworkResponse;
import com.example.yummy_food_planner.data.authentication.model.User;

public interface AuthRepository {
    void signup(String email, String password,AuthNetworkResponse response);
    void signIn(User user,AuthNetworkResponse response);
}