package com.example.yummy_food_planner.presentation.authentication.signin.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthNetworkResponse;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.data.authentication.model.User;
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

public class SignInPresenterImp implements SignInPresenter{

    private AuthRepository repository;
    private SignInView view;
    private Context context;
    private GoogleSignInClient googleSignInClient;

    public SignInPresenterImp(SignInView view,Context context){
        repository = new AuthRepositoryImp();
        this.view = view;
        this.context = context;

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
                view.showError(context.getString(R.string.failed_to_get_google_account), SignInErrorType.GOOGLE_ACCOUNT);
                return;
            }

            repository.signInWithGoogle(account, new AuthNetworkResponse() {
                @Override
                public void onSuccess(User user) {
                    view.onSignInSuccess();
                }

                @Override
                public void onError(Exception exception) {
                    handleGoogleError(exception);
                }
            });

        } catch (ApiException e) {
            handleGoogleApiError(e);
        }

    }

    @Override
    public void signInWithEmailAndPassword(String email,String password) {
        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.onNoInternet();
            return;
        }

        if(!isValidEmail(email)) return;
        if(!isValidPassword(password)) return;

        repository.signInWithEmailAndPassword(email, password, new AuthNetworkResponse() {
            @Override
            public void onSuccess(User user) {
                view.onSignInSuccess();
            }

            @Override
            public void onError(Exception exception) {
              handleError(exception);
            }
        });
    }

    private boolean isValidEmail(String email) {
        if (!Validator.isEmailValid(email)) {
            view.showError("Invalid email format", SignInErrorType.EMAIL);
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (!Validator.isPasswordValid(password)) {
            view.showError("Password must be >=6 chars", SignInErrorType.PASSWORD);
            return false;
        }
        return true;
    }

    private void handleError(Exception exception) {
        if (exception == null || exception.getMessage() == null) {
            view.showError("An error occurred", SignInErrorType.GENERAL);
            return;
        }

        String message = exception.getMessage().toLowerCase();

        if (message.contains("no user record")) {
            view.showError("Email not registered", SignInErrorType.EMAIL);
        } else if (message.contains("wrong password")) {
            view.showError("Incorrect password", SignInErrorType.PASSWORD);
        } else {
            view.showError(exception.getMessage(), SignInErrorType.GENERAL);
        }
    }

    private void handleGoogleError(Exception exception) {
        if (exception == null || exception.getMessage() == null) {
            view.showError("Google sign in failed", SignInErrorType.GOOGLE_ACCOUNT);
            return;
        }
        view.showError(exception.getMessage(), SignInErrorType.GOOGLE_ACCOUNT);
    }

    private void handleGoogleApiError(ApiException e) {
        String errorMessage;

        switch (e.getStatusCode()) {
            case 12500:
                errorMessage = "Sign in was cancelled";
                break;
            case 7:
                errorMessage = "Network connection error";
                break;
            default:
                errorMessage = "Google sign in failed";
                break;
        }

        view.showError(errorMessage, SignInErrorType.GOOGLE_ACCOUNT);
    }
}