package com.example.yummy_food_planner.presentation.mealplan.view;

import com.example.yummy_food_planner.presentation.mealplan.model.MealPlanUiModel;

import java.util.List;

public interface MealPlanView {
    void showMeals(List<MealPlanUiModel> meals);
    void showMessage(int messageId);
}