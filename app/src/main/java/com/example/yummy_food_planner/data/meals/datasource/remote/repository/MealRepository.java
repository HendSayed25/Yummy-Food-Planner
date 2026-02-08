package com.example.yummy_food_planner.data.meals.datasource.remote.repository;

import com.example.yummy_food_planner.data.meals.datasource.remote.response.AreaListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoriesResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoryListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.FilterMealResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.IngredientsListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    Single<MealResponse> getRandomMeal();
    Observable<MealResponse> getMealsByLetter(String letter);
    Single<FilterMealResponse> getMealsByCountry(String c);
    Single<FilterMealResponse> getMealsByCategory(String c);
    Single<FilterMealResponse> getMealsByIngredient(String i);
    Observable<MealResponse> getMealByName(String name);
    Single<CategoriesResponse> getAllCategories();
    Single<AreaListResponse> getAllCountries(String c);
    Single<IngredientsListResponse> getAllIngredients(String i);
    public Single<MealResponse> getMealDetailsById(String mealId);
    public String getIngredientImageUrl(String ingredientName);
}