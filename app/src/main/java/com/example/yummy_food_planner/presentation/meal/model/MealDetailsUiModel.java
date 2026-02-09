package com.example.yummy_food_planner.presentation.meal.model;

public class MealDetailsUiModel {

    private final String id;
    private final String name;
    private final String mealImageUrl;
    private final String category;
    private final String country;

    public MealDetailsUiModel(
            String name,
            String mealImageUrl,
            String category,
            String country,
            String id
    )
    {
        this.id = id;
        this.name = name;
        this.mealImageUrl = mealImageUrl;
        this.category = category;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getMealImageUrl() {
        return mealImageUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }
}