package com.example.yummy_food_planner.data.authentication.datasource.repository;

import com.example.yummy_food_planner.data.authentication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthRepository {
    Single<User> signup(String email, String password);

    Completable signInWithEmailAndPassword(String email, String password);

    Single<User> signInWithGoogle(GoogleSignInAccount account);

    Completable saveUserData(User user);

    Single<Boolean> isUserLoggedIn();

    Single<Boolean> isFirstTime();

    Single<String> getUserName();

    Single<String> getUserId();

    Completable logout();

    Completable setLoggedIn();

    Completable setIsNotFirstTime();

    Single<String> signInAnonymously();

    Single<Boolean> isUserGuest();
    Completable setGuestState(Boolean state);
}