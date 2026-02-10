package com.example.yummy_food_planner.data.model.entitiy;

import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.MEAL_TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.Entity;


@Entity(
        tableName = MEAL_TABLE_NAME,
        primaryKeys = {"id", "userId"}
)
public class Meal {
    @NonNull
    private String id;
    @NonNull
    private String userId;
    private String name;
    private String mealThumb;

    public Meal(String id, String userId, String name, String mealThumb) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.mealThumb = mealThumb;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
    public String getMealThumb() {
        return mealThumb;
    }
}