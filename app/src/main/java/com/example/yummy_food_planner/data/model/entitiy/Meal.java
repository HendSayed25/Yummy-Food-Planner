package com.example.yummy_food_planner.data.model.entitiy;

import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.MEAL_TABLE_NAME;

import androidx.room.Entity;
import java.util.List;


@Entity(
        tableName = MEAL_TABLE_NAME,
        primaryKeys = {"id", "userId"}
)
public class Meal {
    private String id;
    private String userId;
    private String name;
    private String category;
    private String country;
    private String instructions;
    private String mealThumb;
    private String video;
    private List<String> ingredients;
    private List<String> measures;

    public Meal(String id, String userId, String name, String category, String country, String instructions, String mealThumb, String video, List<String> ingredients, List<String> measures) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.country = country;
        this.instructions = instructions;
        this.mealThumb = mealThumb;
        this.video = video;
        this.ingredients = ingredients;
        this.measures = measures;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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