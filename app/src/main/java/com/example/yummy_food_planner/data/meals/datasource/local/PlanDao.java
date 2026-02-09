package com.example.yummy_food_planner.data.meals.datasource.local;

import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.PLAN_TABLE_NAME;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yummy_food_planner.data.model.entitiy.Meal;
import com.example.yummy_food_planner.data.model.entitiy.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface PlanDao {
    @Query("SELECT * FROM "+ PLAN_TABLE_NAME +" WHERE userId = :userId AND date = :date")
    Observable<List<Meal>> getMealByData(String userId, long date);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMeal(Plan meal);
    @Delete
    Completable deleteMeal(Plan meal);
}