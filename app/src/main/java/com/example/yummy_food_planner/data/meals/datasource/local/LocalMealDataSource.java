package com.example.yummy_food_planner.data.meals.datasource.local;

import android.content.Context;

import com.example.yummy_food_planner.data.db.AppDatabase;
import com.example.yummy_food_planner.data.model.entitiy.Meal;
import com.example.yummy_food_planner.data.model.entitiy.Plan;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class LocalMealDataSource {
    private MealDao mealDao;
    private PlanDao planDao;

    public LocalMealDataSource(Context context) {
        mealDao = AppDatabase.getInstance(context).mealDao();
        planDao = AppDatabase.getInstance(context).planDao();
    }

    public Observable<List<Meal>> getAllFavoriteMeals() {
        return mealDao.getAllFavorite();
    }

    public Completable addMealToFavorite(Meal meal, String userId) {
        return mealDao.addToFavorite(meal, userId);
    }

    public Completable deleteMealFromFavorite(String mealId, String userId) {
        return mealDao.deleteFromFavorite(mealId, userId);
    }

    public Observable<List<Plan>> getMealPlansByData(Long date, String userId) {
        return planDao.getMealsByData(userId, date);
    }

    public Completable addMealToPlan(Plan meal, Long date, String userId) {
        return planDao.addMeal(meal, date, userId);
    }

    public Completable deleteMealFromPlan(String mealId, Long date, String userId) {
        return planDao.deleteMeal(mealId, date, userId);
    }
}