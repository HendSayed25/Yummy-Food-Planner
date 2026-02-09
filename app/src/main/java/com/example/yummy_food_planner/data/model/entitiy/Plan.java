package com.example.yummy_food_planner.data.model.entitiy;

import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.PLAN_TABLE_NAME;

import androidx.room.Entity;
import java.util.List;

@Entity(
        tableName = PLAN_TABLE_NAME,
        primaryKeys = {"id", "userId", "date"}
)
public class Plan {
    private int id;
    private String userId;
    private Long date;
    private String name;
    private String category;
    private String country;
    private String instructions;
    private String mealThumb;
    private String video;
    private List<String> ingredients;
    private List<String> measures;

    public Plan(int id, String userId, Long date, String name, String category, String country, String instructions, String mealThumb, String video, List<String> ingredients, List<String> measures) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.name = name;
        this.category = category;
        this.country = country;
        this.instructions = instructions;
        this.mealThumb = mealThumb;
        this.video = video;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Long getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getCountry() {
        return country;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getMealThumb() {
        return mealThumb;
    }

    public String getVideo() {
        return video;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getMeasures() {
        return measures;
    }
}