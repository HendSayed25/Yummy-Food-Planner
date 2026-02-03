package com.example.yummy_food_planner.data.authentication.datasource.remote;

import com.google.firebase.auth.FirebaseAuth;

public class AuthRemoteDatasource {
    private FirebaseAuth auth;

    public AuthRemoteDatasource() {
        auth = FirebaseAuth.getInstance();
    }


    public void signup(String email, String password, AuthNetworkResponse response) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                response.onSignupSuccess(auth.getCurrentUser().getUid());
            } else {
                response.onError(task.getException());
            }
        });
    }
}