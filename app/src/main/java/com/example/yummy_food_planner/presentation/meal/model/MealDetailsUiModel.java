package com.example.yummy_food_planner.presentation.meal.model;

public class MealDetailsUiModel {

    private final String name;
    private final String mealImageUrl;
    private final String category;
    private final String categoryImageUrl;
    private final String country;
    private final String countryImageUrl;

    public MealDetailsUiModel(
            String name,
            String mealImageUrl,
            String category,
            String categoryImageUrl,
            String country,
            String countryImageUrl
    ) {
        this.name = name;
        this.mealImageUrl = mealImageUrl;
        this.category = category;
        this.categoryImageUrl = categoryImageUrl;
        this.country = country;
        this.countryImageUrl = countryImageUrl;
    }

    public String getName() { return name; }
    public String getMealImageUrl() { return mealImageUrl; }
    public String getCategory() { return category; }
    public String getCategoryImageUrl() { return categoryImageUrl; }
    public String getCountry() { return country; }
    public String getCountryImageUrl() { return countryImageUrl; }
}