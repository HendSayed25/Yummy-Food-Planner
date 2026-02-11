package com.example.yummy_food_planner.presentation.meal.view;

import com.example.yummy_food_planner.presentation.meal.model.IngredientUiModel;
import com.example.yummy_food_planner.presentation.meal.model.MealDetailsUiModel;

import java.util.List;

public interface MealDetailsView {
    void showIngredients(List<IngredientUiModel> ingredients);

    void showMealDetails(MealDetailsUiModel meal);

    void showInstructions(String instructions);

    void showMealVideo(String videoId);

    void noInternet();

    void showViews();

    void hideViews();

    void showError(int messageId);

    void showLoading();

    void hideLoading();

    void addedToFav();

    void deleteFromFav();
    void showAddedToMealPlanIcon();
    void showAddToPlanIcon();
}