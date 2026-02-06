package com.example.yummy_food_planner.data.meals.datasource.remote.response;

import com.example.yummy_food_planner.data.model.FilterMealDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterMealResponse {
    @SerializedName("meals")
    private List<FilterMealDto> meals;

    public List<FilterMealDto> getMeals() {
        return meals;
    }
}