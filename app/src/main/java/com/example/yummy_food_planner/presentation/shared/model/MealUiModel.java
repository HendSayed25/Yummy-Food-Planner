package com.example.yummy_food_planner.presentation.shared.model;

public class MealUiModel {
    private String name;
    private String imageUrl;

    public MealUiModel(String name, String imageUrl) {
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
