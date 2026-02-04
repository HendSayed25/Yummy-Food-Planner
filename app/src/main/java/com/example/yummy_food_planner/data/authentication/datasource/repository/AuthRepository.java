package com.example.yummy_food_planner.data.authentication.datasource.repository;

import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthNetworkResponse;
import com.example.yummy_food_planner.data.authentication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface AuthRepository {
    void signup(String email, String password,AuthNetworkResponse response);
    void signInWithEmailAndPassword(String email,String password,AuthNetworkResponse response);
    void signInWithGoogle(GoogleSignInAccount account, AuthNetworkResponse response);
    void saveUserData(User user);
}