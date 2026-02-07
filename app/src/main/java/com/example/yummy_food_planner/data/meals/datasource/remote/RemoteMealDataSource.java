package com.example.yummy_food_planner.data.meals.datasource.remote;

import com.example.yummy_food_planner.data.meals.datasource.remote.response.AreaListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoriesResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoryListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.FilterMealResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.IngredientsListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;
import com.example.yummy_food_planner.data.network.NetworkClient;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class RemoteMealDataSource {

    private MealService mealService;

    public RemoteMealDataSource(){
        mealService = NetworkClient.getInstance();
    }

    public Single<MealResponse> getRandomMeal(){
        return mealService.getRandomMeal();
    }

    public Observable<MealResponse> getMealsByLetter(String letter){
        return mealService.getMealByFirstLetter(letter);
    }
    public Single<FilterMealResponse> getMealsByCountry(String c){
        return mealService.getMealByArea(c);
    }
    public Single<FilterMealResponse> getMealsByCategory(String c){
        return mealService.getMealByCategory(c);
    }
    public Single<FilterMealResponse> getMealsByIngredient(String i){
       return mealService.getMealByMainIngredient(i);
    }
    public Observable<MealResponse> getMealByName(String name){
        return mealService.getMealByName(name);
    }
    public Single<CategoriesResponse> getAllCategories(){
        return mealService.getMealCategories();
    }
    public Single<AreaListResponse> getAllCountries(String c){
        return mealService.getMealAreaList(c);
    }
    public Single<IngredientsListResponse> getAllIngredients(String i){
        return mealService.getMealIngredientsList(i);
    }
}