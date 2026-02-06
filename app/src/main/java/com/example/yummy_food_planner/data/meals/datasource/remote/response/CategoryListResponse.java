package com.example.yummy_food_planner.data.meals.datasource.remote.response;

import com.example.yummy_food_planner.data.model.CategoryItemDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryListResponse {

    @SerializedName("meals")
    private List<CategoryItemDto> meals;

    public List<CategoryItemDto> getMeals() {
        return meals;
    }
}