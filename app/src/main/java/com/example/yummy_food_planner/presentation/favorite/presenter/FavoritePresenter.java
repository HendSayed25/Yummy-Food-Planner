package com.example.yummy_food_planner.presentation.favorite.presenter;


public interface FavoritePresenter {
    void showAllFavorites();
    void removeFromFavorite(String mealId);
    void onDestroy();
}