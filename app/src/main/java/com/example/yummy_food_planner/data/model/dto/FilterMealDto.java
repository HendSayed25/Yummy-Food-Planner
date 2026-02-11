package com.example.yummy_food_planner.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class FilterMealDto {
    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String name;

    @SerializedName("strMealThumb")
    private String image;

    public String getIdMeal() {
        return idMeal;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
