package com.example.yummy_food_planner.data.authentication.datasource.repository;


import android.util.Log;

import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthNetworkResponse;
import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthRemoteDatasource;
import com.example.yummy_food_planner.data.authentication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AuthRepositoryImp implements AuthRepository {

    private AuthRemoteDatasource datasource;

    public AuthRepositoryImp() {
        datasource = new AuthRemoteDatasource();
    }

    @Override
    public void signup(String email, String password, AuthNetworkResponse response) {
        datasource.signup(email, password, response);
    }

    @Override
    public void signInWithEmailAndPassword(String email,String password, AuthNetworkResponse response) {
       datasource.signInWithEmailAndPassword(email,password,response);
    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account, AuthNetworkResponse response) {
        Log.e("TAG","In repo");

        datasource.signInWithGoogle(account,response);
    }


    @Override
    public void saveUserData(User user) {
        //TODO save it in local database
    }

    private String generateNameFromEmail(String email) {
        if (email == null || !email.contains("@")) return "User";

        String namePart = email.split("@")[0];
        String[] parts = namePart.split("[._]");

        StringBuilder name = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                name.append(part.substring(0, 1).toUpperCase())
                        .append(part.substring(1)).
                        append(" ");
            }
        }

        return name.toString().trim();
    }
}