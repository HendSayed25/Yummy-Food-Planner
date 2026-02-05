package com.example.yummy_food_planner.data.authentication.datasource.repository;


import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthRemoteDatasource;
import com.example.yummy_food_planner.data.authentication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRepositoryImp implements AuthRepository {

    private AuthRemoteDatasource datasource;

    public AuthRepositoryImp() {
        datasource = new AuthRemoteDatasource();
    }

    @Override
    public Single<User> signup(String email, String password) {
        return datasource.signup(email, password);
    }

    @Override
    public Completable signInWithEmailAndPassword(String email, String password) {
        return datasource.signInWithEmailAndPassword(email,password);
    }

    @Override
    public Single<User> signInWithGoogle(GoogleSignInAccount account) {
        return  datasource.signInWithGoogle(account);
    }


    @Override
    public Completable saveUserData(User user) {
        //TODO save it in local database
        return null;
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