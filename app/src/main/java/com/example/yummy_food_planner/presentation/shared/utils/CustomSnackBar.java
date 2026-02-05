package com.example.yummy_food_planner.presentation.shared.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class CustomSnackBar {

    public static void showSnackBar(View view, String message, int backgroundColor, int textColor){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(backgroundColor);
        snackbar.setTextColor(textColor);
        snackbar.show();
    }
}
