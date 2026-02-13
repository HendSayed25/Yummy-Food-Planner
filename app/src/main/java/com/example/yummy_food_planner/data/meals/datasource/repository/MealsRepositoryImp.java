package com.example.yummy_food_planner.data.meals.datasource.repository;

import android.content.Context;

import com.example.yummy_food_planner.data.authentication.datasource.remote.AuthRemoteDatasource;
import com.example.yummy_food_planner.data.meals.datasource.local.LocalMealDataSource;
import com.example.yummy_food_planner.data.meals.datasource.remote.RemoteMealDataSource;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.AreaListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoriesResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.FilterMealResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.IngredientsListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;
import com.example.yummy_food_planner.data.model.entitiy.Meal;
import com.example.yummy_food_planner.data.model.entitiy.Plan;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImp implements MealRepository {

    private RemoteMealDataSource remoteMealDataSource;
    private LocalMealDataSource localMealDataSource;
    private AuthRemoteDatasource auth;

    public MealsRepositoryImp(Context context) {
        remoteMealDataSource = new RemoteMealDataSource();
        localMealDataSource = new LocalMealDataSource(context.getApplicationContext());
        auth = new AuthRemoteDatasource();
    }

    @Override
    public Single<MealResponse> getRandomMeal() {
        return remoteMealDataSource.getRandomMeal();
    }

    @Override
    public Observable<MealResponse> getMealsByLetter(String letter) {
        return remoteMealDataSource.getMealsByLetter(letter);
    }

    @Override
    public Single<FilterMealResponse> getMealsByCountry(String c) {
        return remoteMealDataSource.getMealsByCountry(c);
    }

    @Override
    public Single<FilterMealResponse> getMealsByCategory(String c) {
        return remoteMealDataSource.getMealsByCategory(c);
    }

    @Override
    public Single<FilterMealResponse> getMealsByIngredient(String i) {
        return remoteMealDataSource.getMealsByIngredient(i);
    }

    @Override
    public Observable<MealResponse> getMealByName(String name) {
        return remoteMealDataSource.getMealByName(name);
    }

    @Override
    public Single<CategoriesResponse> getAllCategories() {
        return remoteMealDataSource.getAllCategories();
    }

    @Override
    public Single<AreaListResponse> getAllCountries(String c) {
        return remoteMealDataSource.getAllCountries(c);
    }

    @Override
    public Single<IngredientsListResponse> getAllIngredients(String i) {
        return remoteMealDataSource.getAllIngredients(i);
    }

    @Override
    public Single<MealResponse> getMealDetailsById(String mealId) {
        return remoteMealDataSource.getMealDetailsById(mealId);
    }

    @Override
    public String getIngredientImageUrl(String ingredientName) {
        return remoteMealDataSource.getIngredientImageUrl(ingredientName);
    }

    @Override
    public Observable<List<Meal>> getAllFavoriteMeals(String userId) {
        return localMealDataSource.getAllFavoriteMeals(userId);
    }

    @Override
    public Observable<List<Plan>> getPlanedMealsByDate(Long date, String userId) {
        return localMealDataSource.getMealPlansByData(date, userId);
    }

    @Override
    public Completable addMealToFavorite(Meal meal) {
        return localMealDataSource.addMealToFavorite(meal);
    }

    @Override
    public Single<Boolean> isMealFavorite(String mealId, String userId) {
        return localMealDataSource.isMealFavorite(mealId, userId);
    }

    @Override
    public Completable deleteMealFromFavorite(String mealId, String userId) {
        return localMealDataSource.deleteMealFromFavorite(mealId, userId);
    }

    @Override
    public Completable addMealToPlan(Plan meal) {
        return localMealDataSource.addMealToPlan(meal);
    }

    @Override
    public Completable deleteMealFromPlan(String mealId, Long date, String userId) {
        return localMealDataSource.deleteMealFromPlan(mealId, date, userId);
    }

    @Override
    public Single<Boolean> isMealPlaned(String mealId, String userId) {
        return localMealDataSource.isMealPlaned(mealId, userId);
    }

    @Override
    public Completable syncUserData() {
        Observable<List<Meal>> favoriteMeals = localMealDataSource.getAllFavoriteMeals(auth.getCurrentUser().getId());

        Single<List<Plan>> mealPlans = localMealDataSource.getAllMealPlans();

        return favoriteMeals.firstOrError()
                .flatMapCompletable(meals ->
                        mealPlans.flatMapCompletable(plans ->
                                Completable.create(emitter -> {
                                    String uid = auth.getCurrentUser().getId();
                                    if (uid == null) {
                                        emitter.onError(new Exception("No user logged in"));
                                        return;
                                    }

                                    Map<String, Object> data = new HashMap<>();
                                    data.put("favoriteMeals", meals);
                                    data.put("mealPlans", plans);

                                    FirebaseFirestore.getInstance()
                                            .collection("users")
                                            .document(uid)
                                            .set(data, SetOptions.merge())
                                            .addOnSuccessListener(aVoid -> emitter.onComplete())
                                            .addOnFailureListener(emitter::onError);
                                })
                        )
                );
    }
}