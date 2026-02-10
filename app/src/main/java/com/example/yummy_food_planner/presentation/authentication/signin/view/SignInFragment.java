package com.example.yummy_food_planner.presentation.authentication.signin.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.authentication.signin.presenter.SignInPresenter;
import com.example.yummy_food_planner.presentation.authentication.signin.presenter.SignInPresenterImp;
import com.example.yummy_food_planner.presentation.shared.utils.CustomSnackBar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

public class SignInFragment extends Fragment implements SignInView {

    private TextView signUpBtn;
    private AppCompatButton signInBtn,googleBtn,guestBtn;
    private TextInputEditText etEmail , etPassword;
    private SignInPresenter presenter;
    private ActivityResultLauncher<Intent> googleSignInLauncher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUpBtn = view.findViewById(R.id.signupTv);
        signInBtn = view.findViewById(R.id.loginBtn);
        googleBtn = view.findViewById(R.id.googleBtn);
        guestBtn = view.findViewById(R.id.guestBtn);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        presenter = new SignInPresenterImp(this,getContext());

        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        presenter.handleGoogleSignInResult(task);
                    }
                }
        );

        signUpBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_signUpFragment);
        });

        signInBtn.setOnClickListener(v->{
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            presenter.signInWithEmailAndPassword(email,password);
        });

        googleBtn.setOnClickListener(v ->{
            presenter.signInWithGoogle(this, googleSignInLauncher);
        });


        guestBtn.setOnClickListener(v->{
            ///TODO restrict it's action
            Navigation.findNavController(getView()).navigate(R.id.action_signInFragment_to_homeFragment);
        });
    }

    @Override
    public void onSignInSuccess() {
        Navigation.findNavController(getView()).navigate(R.id.action_signInFragment_to_homeFragment);
    }

    @Override
    public void showError(int messageId, SignInErrorType type) {
        switch (type) {
            case EMAIL:
                etEmail.setError(getString(messageId));
                etPassword.setError(null);
                break;

            case PASSWORD:
               etPassword.setError(getString(messageId));
                etEmail.setError(null);
                break;

            case GOOGLE_ACCOUNT:
            case GENERAL:
                CustomSnackBar.showSnackBar(requireView(),getString(messageId),getResources().getColor(R.color.logo_bg),getResources().getColor(R.color.blue_primary));
                break;
        }
    }

    @Override
    public void onNoInternet() {
        CustomSnackBar.showSnackBar(requireView(),getString(R.string.no_internet),getResources().getColor(R.color.logo_bg),getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}