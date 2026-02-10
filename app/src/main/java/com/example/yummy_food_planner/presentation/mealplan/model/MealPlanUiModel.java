package com.example.yummy_food_planner.presentation.mealplan.model;

public class MealPlanUiModel {
    private String name;
    private String imageUrl;
    private String id;
    private long dateMillis;

    public MealPlanUiModel(String name, String imageUrl, String id, long dateMillis) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
        this.dateMillis = dateMillis;
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

    public long getDateMillis() {
        return dateMillis;
    }
}