package com.example.yummy_food_planner.presentation.search.view;

import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;

import java.util.List;

public interface SearchViews {
    void showMeals(List<MealUiModel> meals);
    void showViews();
    void hideViews();
    void showLoading();
    void hideLoading();
    void showError(int messageId);
    void noInternet();
}
