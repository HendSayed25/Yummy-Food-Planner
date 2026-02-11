package com.example.yummy_food_planner.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class CategoryDto {

    @SerializedName("idCategory")
    private String id;

    @SerializedName("strCategory")
    private String name;

    @SerializedName("strCategoryThumb")
    private String image;

    @SerializedName("strCategoryDescription")
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}