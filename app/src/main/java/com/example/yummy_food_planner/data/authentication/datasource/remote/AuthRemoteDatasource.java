package com.example.yummy_food_planner.data.authentication.datasource.remote;

import android.util.Log;

import com.example.yummy_food_planner.data.authentication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthRemoteDatasource {
    private FirebaseAuth auth;

    public AuthRemoteDatasource() {
        auth = FirebaseAuth.getInstance();
    }


    public void signup(String email, String password, AuthNetworkResponse response) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                response.onSuccess(firebaseUserMapping(auth.getCurrentUser()));
            } else {
                response.onError(task.getException());
            }
        });
    }

    public void signInWithEmailAndPassword(String email,String password, AuthNetworkResponse response) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                response.onSuccess(firebaseUserMapping(auth.getCurrentUser()));
            } else {
                response.onError(task.getException());
            }
        });
    }

    public void signInWithGoogle(GoogleSignInAccount account, AuthNetworkResponse response) {
        Log.e("TAG","account is "+account.getIdToken());

        if (account == null || account.getIdToken() == null) {
            response.onError(new Exception("Google account token is null"));
            return;
        }

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.e("TAG",auth.getCurrentUser().getEmail());
                        response.onSuccess(firebaseUserMapping(auth.getCurrentUser()));
                    } else {
                        Log.e("TAG",task.getException().toString());

                        response.onError(task.getException());
                    }
                });
    }

    private User firebaseUserMapping(FirebaseUser user) {
        if (user != null) {
            return new User(
                    user.getUid(),
                    user.getEmail(),
                    user.getDisplayName()
            );
        }
        return null;
    }
}