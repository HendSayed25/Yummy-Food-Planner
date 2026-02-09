package com.example.yummy_food_planner.data.meals.datasource.repository;

import android.content.Context;

import com.example.yummy_food_planner.data.meals.datasource.local.LocalMealDataSource;
import com.example.yummy_food_planner.data.meals.datasource.remote.RemoteMealDataSource;
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

public class MealsRepositoryImp implements MealRepository {

    private RemoteMealDataSource remoteMealDataSource;
    private LocalMealDataSource localMealDataSource;

    public MealsRepositoryImp(Context context) {
        remoteMealDataSource = new RemoteMealDataSource();
        localMealDataSource = new LocalMealDataSource(context.getApplicationContext());
    }

    @Override
    public Single<MealResponse> getRandomMeal() {
        return remoteMealDataSource.getRandomMeal();
    }

    @Override
    public Observable<MealResponse> getMealsByLetter(String letter) {
        return remoteMealDataSource.getMealsByLetter(letter);
    }

    @Override
    public Single<FilterMealResponse> getMealsByCountry(String c) {
        return remoteMealDataSource.getMealsByCountry(c);
    }

    @Override
    public Single<FilterMealResponse> getMealsByCategory(String c) {
        return remoteMealDataSource.getMealsByCategory(c);
    }

    @Override
    public Single<FilterMealResponse> getMealsByIngredient(String i) {
        return remoteMealDataSource.getMealsByIngredient(i);
    }

    @Override
    public Observable<MealResponse> getMealByName(String name) {
        return remoteMealDataSource.getMealByName(name);
    }

    @Override
    public Single<CategoriesResponse> getAllCategories() {
        return remoteMealDataSource.getAllCategories();
    }

    @Override
    public Single<AreaListResponse> getAllCountries(String c) {
        return remoteMealDataSource.getAllCountries(c);
    }

    @Override
    public Single<IngredientsListResponse> getAllIngredients(String i) {
        return remoteMealDataSource.getAllIngredients(i);
    }

    @Override
    public Single<MealResponse> getMealDetailsById(String mealId) {
        return remoteMealDataSource.getMealDetailsById(mealId);
    }

    @Override
    public String getIngredientImageUrl(String ingredientName) {
        return remoteMealDataSource.getIngredientImageUrl(ingredientName);
    }

    @Override
    public Observable<List<Meal>> getAllFavoriteMeals() {
        return localMealDataSource.getAllFavoriteMeals();
    }

    @Override
    public Observable<List<Plan>> getMealPlansByDate(Long date, String userId) {
        return localMealDataSource.getMealPlansByData(date, userId);
    }

    @Override
    public Completable addMealToFavorite(Meal meal, String userId) {
        return localMealDataSource.addMealToFavorite(meal, userId);
    }

    @Override
    public Completable deleteMealFromFavorite(String mealId, String userId) {
        return localMealDataSource.deleteMealFromFavorite(mealId, userId);
    }

    @Override
    public Completable addMealToPlan(Plan meal, Long date, String userId) {
        return localMealDataSource.addMealToPlan(meal, date, userId);
    }

    @Override
    public Completable deleteMealFromPlan(String mealId, Long date, String userId) {
        return localMealDataSource.deleteMealFromPlan(mealId, date, userId);
    }
}