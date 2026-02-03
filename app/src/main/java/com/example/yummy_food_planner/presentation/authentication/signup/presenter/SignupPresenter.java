package com.example.yummy_food_planner.presentation.authentication.signup.presenter;

import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthNetworkResponse;
import com.example.yummy_food_planner.data.authentication.model.User;

public interface SignupPresenter {
    void signup(String email, String password, String confirmedPassword);
}