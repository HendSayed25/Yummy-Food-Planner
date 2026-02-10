package com.example.yummy_food_planner.data.meals.datasource.local;

import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.MEAL_TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yummy_food_planner.data.model.entitiy.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealDao {

    @Query("SELECT * FROM " + MEAL_TABLE_NAME + " WHERE userId= :userId")
    Observable<List<Meal>> getAllFavorite(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToFavorite(Meal meal);

    @Query("DELETE FROM " + MEAL_TABLE_NAME + " WHERE id =:mealId AND userID= :userId")
    Completable deleteFromFavorite(String mealId, String userId);

    @Query("SELECT COUNT(*) FROM " + MEAL_TABLE_NAME + " WHERE id = :mealId AND userId = :userId")
    Single<Integer> isMealFavorite(String mealId, String userId);
}