package com.example.yummy_food_planner.presentation.profile.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.profile.presenter.ProfilePresenter;
import com.example.yummy_food_planner.presentation.profile.presenter.ProfilePresenterImp;
import com.example.yummy_food_planner.presentation.shared.utils.CustomSnackBar;


public class ProfileFragment extends Fragment implements ProfileView {

    private AppCompatButton logOutBtn, syncBtn;
    private ProfilePresenter presenter;
    private TextView usernameValue, emailValue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logOutBtn = view.findViewById(R.id.logOutBtn);
        usernameValue = view.findViewById(R.id.userNameValue);
        emailValue = view.findViewById(R.id.emailValue);
        syncBtn = view.findViewById(R.id.syncBtn);
        presenter = new ProfilePresenterImp(getContext(), this);

        presenter.isUserGuest();

        logOutBtn.setOnClickListener(v -> presenter.logOut());

        syncBtn.setOnClickListener(v -> presenter.syncUserData());
    }

    @Override
    public void showUserData(String userName, String userEmail) {
        usernameValue.setText(userName);
        emailValue.setText(userEmail);
    }

    @Override
    public void showError(int messageId) {
        CustomSnackBar.showSnackBar(requireView(), getString(messageId), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void userIsGuest() {
        logOutBtn.setText(R.string.sign_in);
    }

    @Override
    public void userNotAGuest() {
        presenter.getUserData();
        logOutBtn.setText(R.string.log_out);
    }

    @Override
    public void syncDataSuccessfully() {
        CustomSnackBar.showSnackBar(requireView(), getString(R.string.sync_data_successfully), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void noInternet() {
        CustomSnackBar.showSnackBar(requireView(), getString(R.string.no_internet), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void logOut() {
        NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_signInFragment);
    }
}