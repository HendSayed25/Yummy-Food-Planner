package com.example.yummy_food_planner.data.authentication.datasource.repository;


import android.content.Context;

import com.example.yummy_food_planner.data.authentication.datasource.local.AuthLocalDataSource;
import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthRemoteDatasource;
import com.example.yummy_food_planner.data.authentication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRepositoryImp implements AuthRepository {

    private AuthRemoteDatasource remoteDatasource;
    private AuthLocalDataSource localDataSource;

    public AuthRepositoryImp(Context context) {
        remoteDatasource = new AuthRemoteDatasource();
        localDataSource = new AuthLocalDataSource(context);
    }

    @Override
    public Single<User> signup(String email, String password) {
        return remoteDatasource.signup(email, password);
    }

    @Override
    public Completable signInWithEmailAndPassword(String email, String password) {
        return remoteDatasource.signInWithEmailAndPassword(email, password);
    }

    @Override
    public Single<User> signInWithGoogle(GoogleSignInAccount account) {
        return remoteDatasource.signInWithGoogle(account);
    }

    @Override
    public Completable saveUserData(User user) {
        user.setUsername(generateNameFromEmail(user.getEmail()));

        return Completable.fromAction(() ->
                localDataSource.saveUserData(user.getId(), user.getUsername())
        );
    }

    @Override
    public Single<Boolean> isUserLoggedIn() {
        return Single.fromCallable(localDataSource::isLoggedIn);
    }

    @Override
    public Single<Boolean> isFirstTime() {
        return Single.fromCallable(localDataSource::isFirstTime);
    }

    @Override
    public Single<String> getUserName() {
        return Single.fromCallable(localDataSource::getUserName);
    }

    @Override
    public Single<String> getUserId() {
        return Single.fromCallable(localDataSource::getUserID);
    }

    @Override
    public Completable logout() {
        return Completable.fromAction(localDataSource::logOut);
    }

    @Override
    public Completable setLoggedIn() {
        return Completable.fromAction(localDataSource::setLoggedIn);
    }

    @Override
    public Completable setIsNotFirstTime() {
        return Completable.fromAction(localDataSource::setIsNotFirstTime);
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