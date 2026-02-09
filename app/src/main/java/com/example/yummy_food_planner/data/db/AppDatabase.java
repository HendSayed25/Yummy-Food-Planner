package com.example.yummy_food_planner.data.db;


import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.DATABASE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.yummy_food_planner.data.meals.datasource.local.MealDao;
import com.example.yummy_food_planner.data.meals.datasource.local.PlanDao;
import com.example.yummy_food_planner.data.model.entitiy.Meal;
import com.example.yummy_food_planner.data.model.entitiy.Plan;



@Database(entities = {Plan.class, Meal.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MealDao mealDao();
    public abstract PlanDao planDao();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,DATABASE_NAME).build();
            }
        }
        return instance;
    }
}