package com.example.yummy_food_planner.data.meals.datasource.remote.response;

import com.example.yummy_food_planner.data.model.dto.IngredientDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientsListResponse {

    @SerializedName("meals")
    private List<IngredientDto> meals;

    public List<IngredientDto> getMeals() {
        return meals;
    }
}