package com.example.yummy_food_planner.presentation.authentication.signin.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.data.authentication.utils.Validator;
import com.example.yummy_food_planner.presentation.authentication.signin.view.SignInErrorType;
import com.example.yummy_food_planner.presentation.authentication.signin.view.SignInView;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignInPresenterImp implements SignInPresenter {

    private final AuthRepository repository;
    private final SignInView view;
    private final Context context;
    private GoogleSignInClient googleSignInClient;
    private final CompositeDisposable compositeDisposable;

    public SignInPresenterImp(SignInView view, Context context) {
        repository = new AuthRepositoryImp(context);
        this.view = view;
        this.context = context;
        this.compositeDisposable = new CompositeDisposable();

        setupGoogleSignIn();
    }

    private void setupGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    @Override
    public void signInWithGoogle(Fragment fragment, ActivityResultLauncher<Intent> launcher) {
        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.onNoInternet();
            return;
        }

        Intent signInIntent = googleSignInClient.getSignInIntent();
        launcher.launch(signInIntent);
    }

    @Override
    public void handleGoogleSignInResult(Task<GoogleSignInAccount> task) {
        try {

            GoogleSignInAccount account = task.getResult(ApiException.class);

            if (account == null || account.getIdToken() == null) {
                view.showError(R.string.failed_to_get_google_account, SignInErrorType.GOOGLE_ACCOUNT);
                return;
            }

            compositeDisposable.add(
                    repository.signInWithGoogle(account)
                            .subscribeOn(Schedulers.io())
                            .flatMapCompletable(user ->
                                    repository.saveUserData(user)
                                            .andThen(repository.setLoggedIn())
                                            .andThen(repository.setGuestState(false))
                                            .andThen(Completable.fromAction(() -> view.onSignInSuccess()))
                            ).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> {
                                    },
                                    this::handleGoogleError
                            )
            );

        } catch (ApiException e) {
            handleGoogleApiError(e);
        }

    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.onNoInternet();
            return;
        }

        if (!isValidEmail(email)) return;
        if (!isValidPassword(password)) return;

        compositeDisposable.add(
                repository.signInWithEmailAndPassword(email, password)
                        .andThen(repository.setLoggedIn())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(
                                view::onSignInSuccess,
                                this::handleError
                        )
        );
    }

    @Override
    public void signInAsGuest() {
        compositeDisposable.add(
                repository.signInAnonymously()
                        .flatMapCompletable(uid -> repository.setGuestState(true))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::onSignInSuccess,
                                throwable -> view.showError(R.string.something_went_wrong, SignInErrorType.GENERAL)
                        )
        );
    }

    private boolean isValidEmail(String email) {
        if (!Validator.isEmailValid(email)) {
            view.showError(R.string.invalid_email_format, SignInErrorType.EMAIL);
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (!Validator.isPasswordValid(password)) {
            view.showError(R.string.password_min_length, SignInErrorType.PASSWORD);
            return false;
        }
        return true;
    }

    private void handleError(Throwable throwable) {
        if (throwable instanceof Exception) {
            Exception e = (Exception) throwable;
            String message = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (message.contains("no user record")) {
                view.showError(R.string.email_not_registered, SignInErrorType.EMAIL);
            } else if (message.contains("wrong password")) {
                view.showError(R.string.incorrect_password, SignInErrorType.PASSWORD);
            } else {
                view.showError(R.string.general_error, SignInErrorType.GENERAL);
            }
        } else {
            view.showError(R.string.unexpected_system_error, SignInErrorType.GENERAL);
        }
    }

    private void handleGoogleError(Throwable throwable) {
        if (throwable instanceof Exception) {
            Exception exception = (Exception) throwable;
            if (exception.getMessage() == null) {
                view.showError(R.string.google_sign_in_failed, SignInErrorType.GOOGLE_ACCOUNT);
            } else {
                view.showError(R.string.google_sign_in_failed, SignInErrorType.GOOGLE_ACCOUNT);
            }
        } else {
            view.showError(R.string.unexpected_system_error, SignInErrorType.GENERAL);
        }
    }

    private void handleGoogleApiError(ApiException e) {
        int errorMessageRes;
        switch (e.getStatusCode()) {
            case 12500:
                errorMessageRes = R.string.google_sign_in_cancelled;
                break;
            case 7:
                errorMessageRes = R.string.network_connection_error;
                break;
            default:
                errorMessageRes = R.string.google_sign_in_failed;
                break;
        }
        view.showError(errorMessageRes, SignInErrorType.GOOGLE_ACCOUNT);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}