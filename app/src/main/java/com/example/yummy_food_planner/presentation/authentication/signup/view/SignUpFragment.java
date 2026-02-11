package com.example.yummy_food_planner.presentation.authentication.signup.view;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.authentication.signup.presenter.SignupPresenter;
import com.example.yummy_food_planner.presentation.authentication.signup.presenter.SignupPresenterImp;
import com.example.yummy_food_planner.presentation.shared.utils.CustomSnackBar;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends Fragment implements SignupView {

    private TextView signInTv;
    private Button signupBtn;
    private SignupPresenter presenter;
    private TextInputEditText emailEdt, passwordEdt, confirmPasswordEdt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInTv = view.findViewById(R.id.signInTv);
        signupBtn = view.findViewById(R.id.signUpBtn);
        presenter = new SignupPresenterImp(this, getContext());
        emailEdt = view.findViewById(R.id.etEmailSignUp);
        passwordEdt = view.findViewById(R.id.etPasswordSignUp);
        confirmPasswordEdt = view.findViewById(R.id.etConfirmPassword);


        signInTv.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_signInFragment);
        });

        signupBtn.setOnClickListener(v -> {
            String email = emailEdt.getText().toString();
            String password = passwordEdt.getText().toString();
            String confirmPassword = confirmPasswordEdt.getText().toString();

            presenter.signup(email, password, confirmPassword);
        });

    }

    @Override
    public void showError(int messageId, SignupErrorType type) {
        switch (type) {
            case EMAIL:
                emailEdt.setError(getString(messageId));
                break;

            case PASSWORD:
                passwordEdt.setError(getString(messageId));
                break;

            case CONFIRM_PASSWORD:
                confirmPasswordEdt.setError(getString(messageId));
                break;

            case GENERAL:
                CustomSnackBar.showSnackBar(requireView(), getString(messageId), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
                break;
        }
    }

    @Override
    public void onSignupSuccess() {
        NavHostFragment.findNavController(this).navigate(R.id.action_signUpFragment_to_homeFragment);
    }

    @Override
    public void noInternet() {
        CustomSnackBar.showSnackBar(requireView(), getString(R.string.no_internet), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}