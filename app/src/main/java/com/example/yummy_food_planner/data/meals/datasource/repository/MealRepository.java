package com.example.yummy_food_planner.data.meals.datasource.repository;

import com.example.yummy_food_planner.data.meals.datasource.remote.response.AreaListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoriesResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.FilterMealResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.IngredientsListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;
import com.example.yummy_food_planner.data.model.entitiy.Meal;
import com.example.yummy_food_planner.data.model.entitiy.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
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

    Single<MealResponse> getMealDetailsById(String mealId);

    String getIngredientImageUrl(String ingredientName);

    Observable<List<Meal>> getAllFavoriteMeals();

    Observable<List<Plan>> getMealPlansByDate(Long date, String userId);

    Completable addMealToFavorite(Meal meal);
    Single<Boolean> isMealFavorite(String mealId, String userId);

    Completable deleteMealFromFavorite(String mealId, String userId);

    Completable addMealToPlan(Plan meal);

    Completable deleteMealFromPlan(String mealId, Long date, String userId);
}