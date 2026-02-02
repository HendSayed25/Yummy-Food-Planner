package com.example.yummy_food_planner.presentation.authentication.signin.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yummy_food_planner.R;

public class SignInFragment extends Fragment {

    private TextView signUpBtn;
    private AppCompatButton signInBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signUpBtn = view.findViewById(R.id.signupTv);
        signInBtn = view.findViewById(R.id.loginBtn);

        signUpBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_signUpFragment);
        });

        signInBtn.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_homeFragment);
        });
    }
}