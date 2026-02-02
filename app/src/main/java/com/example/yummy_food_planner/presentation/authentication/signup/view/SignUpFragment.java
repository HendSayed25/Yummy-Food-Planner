package com.example.yummy_food_planner.presentation.authentication.signup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yummy_food_planner.R;

public class SignUpFragment extends Fragment {

    TextView signInTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInTv = view.findViewById(R.id.signInTv);

        signInTv.setOnClickListener(v->{
            Navigation.findNavController(v).navigate(R.id.action_signUpFragment_to_signInFragment);
        });
    }
}