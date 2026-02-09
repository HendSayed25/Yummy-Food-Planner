package com.example.yummy_food_planner.data.meals.datasource.remote.repository;

import com.example.yummy_food_planner.data.meals.datasource.remote.RemoteMealDataSource;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.AreaListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoriesResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoryListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.FilterMealResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.IngredientsListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImp implements MealRepository{

    private RemoteMealDataSource remoteMealDataSource;

    public MealsRepositoryImp(){
        remoteMealDataSource = new RemoteMealDataSource();
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
}