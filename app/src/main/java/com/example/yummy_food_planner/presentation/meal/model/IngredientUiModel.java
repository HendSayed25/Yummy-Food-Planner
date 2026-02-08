package com.example.yummy_food_planner.presentation.meal.model;

public class IngredientUiModel {
    private String name;
    private String imageUrl;

    public IngredientUiModel(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}