package com.example.yummy_food_planner.data.meals.datasource.remote.response;

import com.example.yummy_food_planner.data.model.CategoryDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {

    @SerializedName("categories")
    private List<CategoryDto> categories;

    public List<CategoryDto> getCategories() {
        return categories;
    }
}