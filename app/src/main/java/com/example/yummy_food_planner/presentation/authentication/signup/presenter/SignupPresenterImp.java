package com.example.yummy_food_planner.presentation.authentication.signup.presenter;

import android.content.Context;

import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.data.authentication.utils.Validator;
import com.example.yummy_food_planner.presentation.authentication.signup.view.SignupErrorType;
import com.example.yummy_food_planner.presentation.authentication.signup.view.SignupView;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignupPresenterImp implements SignupPresenter {

    private SignupView view;
    private Context context;
    private AuthRepository repository;
    private CompositeDisposable compositeDisposable;

    public SignupPresenterImp(SignupView view, Context context) {
        this.view = view;
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();
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

        compositeDisposable.add(
                repository.signup(email, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> {
                                    repository.saveUserData(user);
                                    view.onSignupSuccess();
                                },
                                this::handleSignupError
                        )
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
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

    private void handleSignupError(Throwable throwable) {
        if (throwable instanceof Exception) {
            Exception exception = (Exception) throwable;
            if (exception instanceof FirebaseAuthUserCollisionException) {
                view.showError("Email already exists", SignupErrorType.EMAIL);
            } else if (exception instanceof FirebaseAuthWeakPasswordException) {
                view.showError("Weak password", SignupErrorType.PASSWORD);
            } else {
                view.showError("Something went wrong, try again", SignupErrorType.GENERAL);
            }
        } else {
            view.showError("Unexpected system error", SignupErrorType.GENERAL);
        }
    }
}