package com.example.yummy_food_planner.presentation.authentication.signup.presenter;

import android.content.Context;

import com.example.yummy_food_planner.presentation.authentication.signup.view.SignupView;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignupPresenterImp implements SignupPresenter {

    private SignupView view;
    private Context context;

    public SignupPresenterImp(SignupView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void signup(String email, String password, String confirmedPassword) {
        if (!isEmailValid(email)) {
            view.showError("invalid email please ensure the email is valid format");
            return;
        }

        if (!isPasswordValid(password)) {
            view.showError("invalid password please ensure password length >=6 numbers");
            return;
        }

        if (!isPasswordConfirmed(password, confirmedPassword)) {
            view.showError("password not match please ensure password you enter is correct ");
            return;
        }

        registerUser(email, password);
    }

    private void registerUser(String email, String password) {
        if (NetworkCheck.isNetworkAvailable(context)) {
            view.showViews();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    view.onSignupSuccess(auth.getCurrentUser().getUid());
                } else {
                    view.showError(Objects.requireNonNull(task.getException()).getMessage());
                }
            });
        } else {
            view.noInternet();
        }
    }


    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) return false;

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailPattern);
    }

    private boolean isPasswordValid(String password) {
        if (password == null || password.isEmpty()) return false;
        return password.length() >= 6;
    }

    public boolean isPasswordConfirmed(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}