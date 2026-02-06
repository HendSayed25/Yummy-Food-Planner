package com.example.yummy_food_planner.data.meals.datasource.remote.response;

import com.example.yummy_food_planner.data.model.MealDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealResponse {

    @SerializedName("meals")
    private List<MealDto> meals;

    public List<MealDto> getMeals() {
        return meals;
    }
}

