package com.example.yummy_food_planner.data.meals.datasource.remote.repository;

import com.example.yummy_food_planner.data.meals.datasource.remote.RemoteMealDataSource;
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
}