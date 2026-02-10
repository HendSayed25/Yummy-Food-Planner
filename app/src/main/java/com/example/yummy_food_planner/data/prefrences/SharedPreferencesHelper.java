package com.example.yummy_food_planner.data.prefrences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private final SharedPreferences sharedPreferences;
    private static SharedPreferencesHelper instance;
    private final SharedPreferences.Editor editor;
    private static final String PREF_NAME = "MyPrefs";
    private final String USER_ID_KEY = "userId";
    private final String USER_NAME = "userName";
    private final String IS_LOGGED_IN = "loggedIn";
    private final String IS_FIRST_TIME = "firstTime";

    private SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPreferencesHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPreferencesHelper.class) {
                instance = new SharedPreferencesHelper(context);
            }
        }
        return instance;
    }

    public void saveUserId(String id) {
        editor.putString(USER_ID_KEY, id).commit();
    }

    public String getUserID() {
        return sharedPreferences.getString(USER_ID_KEY, "");
    }

    public void saveUserName(String name) {
        editor.putString(USER_NAME, name).commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, "Guest");
    }

    public void setLoggedIn() {
        editor.putBoolean(IS_LOGGED_IN, true).apply();
    }

    public Boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void setIsNotFirstTime() {
        editor.putBoolean(IS_FIRST_TIME, false);
    }

    public Boolean isFirstTime() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME, true);
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }
}