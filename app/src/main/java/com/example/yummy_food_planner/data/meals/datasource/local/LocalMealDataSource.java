package com.example.yummy_food_planner.data.meals.datasource.local;

import android.content.Context;

import com.example.yummy_food_planner.data.db.AppDatabase;
import com.example.yummy_food_planner.data.model.entitiy.Meal;
import com.example.yummy_food_planner.data.model.entitiy.Plan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class LocalMealDataSource {
    private MealDao mealDao;
    private PlanDao planDao;

    public LocalMealDataSource(Context context) {
        mealDao = AppDatabase.getInstance(context).mealDao();
        planDao = AppDatabase.getInstance(context).planDao();
    }

    public Observable<List<Meal>> getAllFavoriteMeals(String userId) {
        return mealDao.getAllFavorite(userId);
    }

    public Completable addMealToFavorite(Meal meal) {
        return mealDao.addToFavorite(meal);
    }

    public Single<Boolean> isMealFavorite(String mealId, String userId) {
        return mealDao.isMealFavorite(mealId, userId)
                .map(count -> count > 0);
    }

    public Completable deleteMealFromFavorite(String mealId, String userId) {
        return mealDao.deleteFromFavorite(mealId, userId);
    }

    public Observable<List<Plan>> getMealPlansByData(Long date, String userId) {
        return planDao.getMealsByData(userId, date);
    }

    public Single<List<Plan>> getAllMealPlans() {
        return planDao.getAllMealPlans();
    }

    public Completable addMealToPlan(Plan meal) {
        return planDao.addMeal(meal);
    }

    public Completable deleteMealFromPlan(String mealId, Long date, String userId) {
        return planDao.deleteMeal(mealId, date, userId);
    }

    public Single<Boolean> isMealPlaned(String mealId, String userId) {
        return planDao.isMealPlaned(mealId, userId)
                .map(count -> count > 0);
    }
}