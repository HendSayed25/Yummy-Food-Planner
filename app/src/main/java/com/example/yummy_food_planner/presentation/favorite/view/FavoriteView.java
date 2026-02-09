package com.example.yummy_food_planner.presentation.favorite.view;

import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;

import java.util.List;

public interface FavoriteView {
    void showFavMeals(List<MealUiModel> meals);
    void showMessage(int messageId);
}