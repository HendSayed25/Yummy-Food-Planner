package com.example.yummy_food_planner.presentation.home.view;

import com.example.yummy_food_planner.presentation.home.model.RandomMealUiModel;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;

import java.util.List;

public interface HomeView {
    void showRandomMeal(RandomMealUiModel meal);
    void showMeals(List<MealUiModel> mealUiModels);
    void noInternet();
    void showViews();
    void showError(String message);
    void showLoading();
    void hideLoading();
    void hideViews();
}