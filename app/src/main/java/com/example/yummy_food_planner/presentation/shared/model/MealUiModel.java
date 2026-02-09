package com.example.yummy_food_planner.presentation.shared.model;

public class MealUiModel {
    private String name;
    private String imageUrl;
    private String id;

    public MealUiModel(String name, String imageUrl, String id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }
}