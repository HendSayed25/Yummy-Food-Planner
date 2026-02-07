package com.example.yummy_food_planner.presentation.home.mapper;

import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;
import com.example.yummy_food_planner.data.model.MealDto;
import com.example.yummy_food_planner.presentation.home.model.RandomMealUiModel;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;

import java.util.List;
import java.util.stream.Collectors;

public class HomeMapper {

    public static RandomMealUiModel toUi(MealDto meal) {
        return new RandomMealUiModel(meal.getStrMeal(), meal.getStrCategory(), meal.getStrMealThumb());
    }

    public static List<MealUiModel> toUiList(MealResponse meals) {
        return meals.getMeals().stream()
                .map(meal -> new MealUiModel(
                        meal.getStrMeal(),
                        meal.getStrMealThumb()
                ))
                .collect(Collectors.toList());

    }
}