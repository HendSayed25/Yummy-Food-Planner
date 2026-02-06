package com.example.yummy_food_planner.data.meals.datasource.remote;

import static com.example.yummy_food_planner.data.meals.utils.Constants.AREA_LIST_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.GATEGORIES_LIST;
import static com.example.yummy_food_planner.data.meals.utils.Constants.INGREDIENTS_LIST_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.MEAL_BY_AREA_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.MEAL_BY_CATEGORY_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.MEAL_BY_FIRST_LETTER_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.MEAL_BY_MAIN_INGREDIENT_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.MEAL_BY_NAME_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.MEAL_DETAILS_BY_ID_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.MEAL_GATEGORIES_END_POINT;
import static com.example.yummy_food_planner.data.meals.utils.Constants.RANDOM_MEAL_END_POINT;

import com.example.yummy_food_planner.data.meals.datasource.remote.response.AreaListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoriesResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.CategoryListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.FilterMealResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.IngredientsListResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET(RANDOM_MEAL_END_POINT)
    Single<MealResponse> getRandomMeal();

    @GET(MEAL_DETAILS_BY_ID_END_POINT)
    Single<MealResponse> getMealDetailsById(@Query("i") String id);

    @GET(MEAL_BY_NAME_END_POINT)
    Observable<MealResponse> getMealByName(@Query("s") String name);

    @GET(MEAL_GATEGORIES_END_POINT)
    Single<CategoriesResponse> getMealCategories();

    @GET(GATEGORIES_LIST)
    Single<CategoryListResponse> getMealCategoriesList(@Query("c") String category);

    @GET(AREA_LIST_END_POINT)
    Single<AreaListResponse> getMealAreaList(@Query("a") String area);

    @GET(INGREDIENTS_LIST_END_POINT)
    Single<IngredientsListResponse> getMealIngredientsList(@Query("i") String ingredient);

    @GET(MEAL_BY_AREA_END_POINT)
    Single<FilterMealResponse> getMealByArea(@Query("a") String area);

    @GET(MEAL_BY_MAIN_INGREDIENT_END_POINT)
    Single<FilterMealResponse> getMealByMainIngredient(@Query("i") String ingredient);

    @GET(MEAL_BY_CATEGORY_END_POINT)
    Single<FilterMealResponse> getMealByCategory(@Query("c") String category);

    @GET(MEAL_BY_FIRST_LETTER_END_POINT)
    Observable<MealResponse> getMealByFirstLetter(@Query("f") String letter);
}