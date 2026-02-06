package com.example.yummy_food_planner.data.meals.datasource.remote.response;

import com.example.yummy_food_planner.data.model.AreaItemDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaListResponse {

    @SerializedName("meals")
    private List<AreaItemDto> meals;

    public List<AreaItemDto> getMeals() {
        return meals;
    }
}