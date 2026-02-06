package com.example.yummy_food_planner.data.authentication.datasource.remote;

import com.example.yummy_food_planner.data.authentication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRemoteDatasource {
    private FirebaseAuth auth;

    public AuthRemoteDatasource() {
        auth = FirebaseAuth.getInstance();
    }

    public Single<User> signup(String email, String password) {
        return Single.create(emitter -> {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(firebaseUserMapping(auth.getCurrentUser()));
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    public Completable signInWithEmailAndPassword(String email, String password) {
        return Completable.create(emiiter ->
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emiiter.onComplete();
                    } else {
                        emiiter.onError(task.getException());
                    }
                }));
    }

    public Single<User> signInWithGoogle(GoogleSignInAccount account) {
        return Single.create(emitter -> {
            if (account == null || account.getIdToken() == null) {
                emitter.onError(new Exception("Google account token is null"));
                return;
            }

            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            auth.signInWithCredential(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onSuccess(firebaseUserMapping(auth.getCurrentUser()));
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
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