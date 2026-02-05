package com.example.yummy_food_planner.presentation.authentication.signin.presenter;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public interface SignInPresenter {
    void signInWithGoogle(Fragment fragment, ActivityResultLauncher<Intent> launcher);
    void signInWithEmailAndPassword(String email,String password);
    void handleGoogleSignInResult(Task<GoogleSignInAccount> task);
    void onDestroy();
}