package com.example.yummy_food_planner.data.meals.datasource.remote;

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
}