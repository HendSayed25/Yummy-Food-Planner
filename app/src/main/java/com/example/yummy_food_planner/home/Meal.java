package com.example.yummy_food_planner.home;

public class Meal {
    private String name;
    private String imageUrl;

    public Meal(String name, String imageUrl) {
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
