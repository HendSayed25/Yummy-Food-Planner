package com.example.yummy_food_planner.data.meals.datasource.local;

import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.MEAL_TABLE_NAME;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yummy_food_planner.data.model.entitiy.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface MealDao {

    @Query("SELECT * FROM " + MEAL_TABLE_NAME)
    Observable<List<Meal>> getAllFavorite();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToFavorite(Meal meal, String userId);

    @Query("DELETE FROM " + MEAL_TABLE_NAME + " WHERE id =:mealId AND userID= :userId")
    Completable deleteFromFavorite(String mealId, String userId);
}