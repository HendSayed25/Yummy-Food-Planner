package com.example.yummy_food_planner.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class IngredientDto {

    @SerializedName("idIngredient")
    private String id;

    @SerializedName("strIngredient")
    private String name;

    @SerializedName("strDescription")
    private String description;

    @SerializedName("strThumb")
    private String thumb;

    @SerializedName("strType")
    private String type;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumb() {
        return thumb;
    }

    public String getType() {
        return type;
    }
}