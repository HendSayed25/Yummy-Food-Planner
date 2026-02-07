package com.example.yummy_food_planner.presentation.home.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.home.model.RandomMealUiModel;
import com.example.yummy_food_planner.presentation.home.presenter.HomePresenter;
import com.example.yummy_food_planner.presentation.home.presenter.HomePresenterImp;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.CustomSnackBar;
import com.example.yummy_food_planner.presentation.shared.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private RecyclerView mealRecycler;
    private HomeMealAdapter homeMealAdapter;

    private View mealOfDay;
    private View noInternetLayout;
    private TextView homeTitle1, homeTitle2, homeTitle3;
    private ImageView logoIcon;
    private ProgressBar loading;
    private HomePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealOfDay = view.findViewById(R.id.mealOfDay);
        noInternetLayout = view.findViewById(R.id.noInternetLayout);
        mealRecycler = view.findViewById(R.id.mealsHomeRecycler);
        homeTitle1 = view.findViewById(R.id.title1);
        homeTitle2 = view.findViewById(R.id.home_title2);
        homeTitle3 = view.findViewById(R.id.home_title3);
        logoIcon = view.findViewById(R.id.logo_ic_home);
        loading = view.findViewById(R.id.loadingHome);
        presenter = new HomePresenterImp(this, getContext());
        homeMealAdapter = new HomeMealAdapter();


        presenter.getRandomMeal();
        presenter.getMealsByLetter("e");

    }

    @Override
    public void showRandomMeal(RandomMealUiModel meal) {
        GlideImageLoader.load(requireContext(), meal.getImageUrl(), mealOfDay.findViewById(R.id.ivMealImage));

        TextView mealName = mealOfDay.findViewById(R.id.tvMealName);
        TextView mealDescription = mealOfDay.findViewById(R.id.tvMealDescription);
        mealName.setText(meal.getName());
        mealDescription.setText(meal.getDescription());
    }

    @Override
    public void showMeals(List<MealUiModel> mealUiModels) {
        homeMealAdapter.setData(mealUiModels);
        mealRecycler.setAdapter(homeMealAdapter);
    }

    @Override
    public void noInternet() {
        noInternetLayout.setVisibility(VISIBLE);
    }

    @Override
    public void showViews() {
        noInternetLayout.setVisibility(GONE);
        logoIcon.setVisibility(VISIBLE);
        mealOfDay.setVisibility(VISIBLE);
        homeTitle1.setVisibility(VISIBLE);
        homeTitle2.setVisibility(VISIBLE);
        homeTitle3.setVisibility(VISIBLE);
        mealRecycler.setVisibility(VISIBLE);
    }

    @Override
    public void showError(String message) {
        CustomSnackBar.showSnackBar(requireView(), message, getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void showLoading() {
        loading.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(GONE);
    }

    @Override
    public void hideViews() {
        mealOfDay.setVisibility(GONE);
        logoIcon.setVisibility(GONE);
        homeTitle1.setVisibility(GONE);
        homeTitle2.setVisibility(GONE);
        homeTitle3.setVisibility(GONE);
        mealRecycler.setVisibility(GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}