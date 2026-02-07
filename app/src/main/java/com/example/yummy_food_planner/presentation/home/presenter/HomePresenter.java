package com.example.yummy_food_planner.presentation.home.presenter;

public interface HomePresenter {
   void getRandomMeal();
    void getMealsByLetter(String letter);
    void onDestroy();
}