package com.example.yummy_food_planner.data.authentication.datasource.local;

import android.content.Context;

import com.example.yummy_food_planner.data.prefrences.SharedPreferencesHelper;

public class AuthLocalDataSource {
    private final SharedPreferencesHelper pref;

    public AuthLocalDataSource(Context context) {
        pref = SharedPreferencesHelper.getInstance(context);
    }

    public void saveUserData(String id, String name, String email) {
        pref.saveUserId(id);
        pref.saveUserName(name);
        pref.saveUserEmail(email);
    }

    public String getUserID() {
        return pref.getUserID();
    }

    public String getUserName() {
        return pref.getUserName();
    }

    public String getUserEmail() {
        return pref.getUserEmail();
    }

    public Boolean isLoggedIn() {
        return pref.isLoggedIn();
    }

    public void setLoggedIn() {
        pref.setLoggedIn();
    }

    public void setIsNotFirstTime() {
        pref.setIsNotFirstTime();
    }

    public Boolean isFirstTime() {
        return pref.isFirstTime();
    }

    public Boolean isUserGuest() {
        return pref.isUserGuest();
    }

    public void setGuestState(Boolean state) {
        pref.setGuestState(state);
    }

    public void logOut() {
        pref.logout();
    }
}