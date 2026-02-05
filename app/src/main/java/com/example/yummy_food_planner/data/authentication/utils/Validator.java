package com.example.yummy_food_planner.data.authentication.utils;

public class Validator {

    private Validator(){}

    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) return false;

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailPattern);
    }

    public static boolean isPasswordValid(String password) {
        if (password == null || password.isEmpty()) return false;
        return password.length() >= 6;
    }

    public static boolean isPasswordConfirmed(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}