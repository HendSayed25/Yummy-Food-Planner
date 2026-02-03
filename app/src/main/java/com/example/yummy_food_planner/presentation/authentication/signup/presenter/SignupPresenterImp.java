package com.example.yummy_food_planner.presentation.authentication.signup.presenter;

import android.content.Context;
import android.util.Log;

import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthNetworkResponse;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.data.authentication.utils.Validator;
import com.example.yummy_food_planner.presentation.authentication.signup.view.SignupErrorType;
import com.example.yummy_food_planner.presentation.authentication.signup.view.SignupView;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignupPresenterImp implements SignupPresenter {

    private SignupView view;
    private Context context;
    private AuthRepository repository;

    public SignupPresenterImp(SignupView view, Context context) {
        this.view = view;
        this.context = context;
        repository = new AuthRepositoryImp();
    }

    @Override
    public void signup(String email, String password, String confirmedPassword) {
        if (!isValidEmail(email)) return;
        if (!isValidPassword(password)) return;
        if (!isPasswordConfirmed(password, confirmedPassword)) return;

        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.noInternet();
            return;
        }

        view.showViews();
        repository.signup(email, password, new AuthNetworkResponse() {
            @Override
            public void onSignupSuccess(String userId) {
                view.onSignupSuccess(userId);
            }

            @Override
            public void onError(Exception exception) {
                handleSignupError(exception);
            }
        });
    }

    private boolean isValidEmail(String email) {
        if (!Validator.isEmailValid(email)) {
            view.showError("Invalid email format", SignupErrorType.EMAIL);
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (!Validator.isPasswordValid(password)) {
            view.showError("Password must be >=6 chars", SignupErrorType.PASSWORD);
            return false;
        }
        return true;
    }

    private boolean isPasswordConfirmed(String password, String confirm) {
        if (!Validator.isPasswordConfirmed(password, confirm)) {
            view.showError("Passwords do not match", SignupErrorType.CONFIRM_PASSWORD);
            return false;
        }
        return true;
    }

    private void handleSignupError(Exception exception) {
        if (exception instanceof FirebaseAuthUserCollisionException) {
            view.showError("Email already exists", SignupErrorType.EMAIL);
        } else if (exception instanceof FirebaseAuthWeakPasswordException) {
            view.showError("Weak password", SignupErrorType.PASSWORD);
        } else {
            view.showError("Something went wrong, try again", SignupErrorType.GENERAL);
        }
    }
}