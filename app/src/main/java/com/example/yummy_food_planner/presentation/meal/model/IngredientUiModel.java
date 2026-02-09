package com.example.yummy_food_planner.presentation.meal.model;

public class IngredientUiModel {
    private String name;
    private String imageUrl;
    private String measure;

    public IngredientUiModel(String name, String imageUrl, String measure) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMeasure() {
        return measure;
    }
}