package com.example.yummy_food_planner.data.model;

import com.google.gson.annotations.SerializedName;

public class CategoryItemDto {

    @SerializedName("strCategory")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }
}