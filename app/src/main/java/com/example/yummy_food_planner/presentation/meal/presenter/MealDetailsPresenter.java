package com.example.yummy_food_planner.presentation.meal.presenter;

import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;

public interface MealDetailsPresenter {
    void getMealDetailsById(String id);
    void addToFavorite(MealUiModel meal);
    void addToCalender(MealUiModel meal);
    void onDestroy();
}