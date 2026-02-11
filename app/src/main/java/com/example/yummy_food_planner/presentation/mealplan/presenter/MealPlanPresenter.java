package com.example.yummy_food_planner.presentation.mealplan.presenter;

public interface MealPlanPresenter {
    void getPlannedMealsByData(Long date);
    void deleteMealFromPlan(String mealId, Long date);
    void onDestroy();
}