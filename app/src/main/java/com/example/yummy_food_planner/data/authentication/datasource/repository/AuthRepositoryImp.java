package com.example.yummy_food_planner.data.authentication.datasource.repository;

import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthNetworkResponse;
import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthRemoteDatasource;
import com.example.yummy_food_planner.data.authentication.model.User;

public class AuthRepositoryImp implements AuthRepository {

    private AuthRemoteDatasource datasource;

    public AuthRepositoryImp(){
        datasource = new AuthRemoteDatasource();
    }
    @Override
    public void signup(String email, String password, AuthNetworkResponse response) {
        datasource.signup(email,password,response);
    }

    @Override
    public void signIn(User user,AuthNetworkResponse response) {
    }
}