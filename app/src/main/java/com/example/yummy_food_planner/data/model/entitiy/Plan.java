package com.example.yummy_food_planner.data.model.entitiy;

import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.PLAN_TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = PLAN_TABLE_NAME,
        primaryKeys = {"mealId", "userId", "date"}
)
public class Plan {
    @NonNull
    private String mealId;
    @NonNull
    private String userId;
    @NonNull
    private Long date;
    private String name;
    private String mealThumb;

    public Plan(String userId,String mealId, Long date, String name, String mealThumb) {
        this.userId = userId;
        this.mealId = mealId;
        this.date = date;
        this.name = name;
        this.mealThumb = mealThumb;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }
    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public Long getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
    public String getMealThumb() {
        return mealThumb;
    }
}