package com.example.yummy_food_planner.presentation.authentication.signup.view;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yummy_food_planner.R;

public class SignUpFragment extends Fragment  implements SignupView{

    private TextView signInTv;
    private ConstraintLayout signUpLayout;
    private View noInternetLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInTv = view.findViewById(R.id.signInTv);
        signUpLayout = view.findViewById(R.id.signUpLayout);
        noInternetLayout = view.findViewById(R.id.noInternetLayout);

        signInTv.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_signInFragment);
        });
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onSignupSuccess(String userId) {
        Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_homeFragment);
    }

    @Override
    public void noInternet() {
        signUpLayout.setVisibility(GONE);
        noInternetLayout.setVisibility(VISIBLE);
    }

    @Override
    public void showViews() {
        signUpLayout.setVisibility(VISIBLE);
        noInternetLayout.setVisibility(GONE);
    }
}