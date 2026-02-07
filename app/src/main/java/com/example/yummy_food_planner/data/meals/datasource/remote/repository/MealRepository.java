package com.example.yummy_food_planner.data.meals.datasource.remote.repository;

import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    Single<MealResponse> getRandomMeal();
    Observable<MealResponse> getMealsByLetter(String letter);
}