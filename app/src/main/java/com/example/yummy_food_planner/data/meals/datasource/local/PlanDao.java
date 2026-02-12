package com.example.yummy_food_planner.data.meals.datasource.local;

import static com.example.yummy_food_planner.data.meals.datasource.local.RoomConstants.PLAN_TABLE_NAME;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.yummy_food_planner.data.model.entitiy.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PlanDao {
    @Query("SELECT * FROM " + PLAN_TABLE_NAME + " WHERE userId = :userId AND date = :date")
    Observable<List<Plan>> getMealsByData(String userId, long date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMeal(Plan meal);

    @Query("DELETE FROM " + PLAN_TABLE_NAME + " WHERE mealId = :mealId AND userId = :userId AND date = :date")
    Completable deleteMeal(String mealId, Long date, String userId);

    @Query("SELECT COUNT(*) FROM " + PLAN_TABLE_NAME + " WHERE mealId =:mealId AND userId=:userId")
    Single<Integer> isMealPlaned(String mealId, String userId);

    @Query("SELECT * FROM "+ PLAN_TABLE_NAME)
    Single<List<Plan>> getAllMealPlans();
}