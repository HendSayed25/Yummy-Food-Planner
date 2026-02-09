package com.example.yummy_food_planner.presentation.meal.presenter;

import android.content.Context;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealRepository;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealsRepositoryImp;
import com.example.yummy_food_planner.data.model.MealDto;
import com.example.yummy_food_planner.data.model.entitiy.Meal;
import com.example.yummy_food_planner.presentation.meal.model.IngredientUiModel;
import com.example.yummy_food_planner.presentation.meal.model.MealDetailsUiModel;
import com.example.yummy_food_planner.presentation.meal.view.MealDetailsView;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImp implements MealDetailsPresenter {

    private MealRepository repository;
    private CompositeDisposable compositeDisposable;
    private Context context;
    private MealDetailsView view;

    public MealDetailsPresenterImp(Context context, MealDetailsView view) {
        this.context = context;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
        repository = new MealsRepositoryImp(context);
    }

    @Override
    public void getMealDetailsById(String id) {
        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.noInternet();
            return;
        }

        view.hideViews();
        view.showLoading();
        compositeDisposable.add(
                repository.getMealDetailsById(id).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meal -> {
                                    view.hideLoading();
                                    view.showViews();
                                    extractDetails(meal);
                                },
                                error -> {
                                    if (error instanceof IOException) {
                                        view.noInternet();
                                    } else {
                                        view.hideLoading();
                                        view.showError(R.string.failure_loading);
                                    }
                                }
                        )
        );
    }

    @Override
    public void addToFavorite(MealUiModel meal) {
        compositeDisposable.add(
                repository.addMealToFavorite(new Meal(meal.getId(), "", meal.getName(), meal.getImageUrl())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.addedToFav(),
                                error -> view.showError(R.string.failure_loading)
                        )
        );
    }

    @Override
    public void removeFromFavorite(MealUiModel meal) {
        compositeDisposable.add(
                repository.deleteMealFromFavorite(meal.getId(), "").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) /// ////////////////user id
                        .subscribe(
                                () -> view.removeFromFav(),
                                error -> view.showError(R.string.failure_loading)
                        ) ///TODO get userID
        );
    }

    @Override
    public void addToCalender(MealUiModel meal) {
        /// TODO save to calender
    }

    @Override
    public void checkIfFavorite(String mealId, String userId) {
        compositeDisposable.add(
                repository.isMealFavorite(mealId, userId)
                        .subscribe(
                                isFav -> {
                                    if (isFav) {
                                        view.addedToFav();
                                    } else {
                                        view.removeFromFav();
                                    }
                                },
                                throwable -> view.showError(R.string.failure_loading)
                        )

        );
    }

    private void extractDetails(MealResponse meal) {
        view.showInstructions(meal.getMeals().get(0).getStrInstructions());
        getIngredients(meal);
        view.showMealVideo(getYouTubeId(meal.getMeals().get(0).getStrYoutube()));
        getMealDetails(meal);
    }

    private String getYouTubeId(String url) {
        Pattern pattern = Pattern.compile("v=([a-zA-Z0-9_-]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private void getIngredients(MealResponse meal) {
        List<IngredientUiModel> ingredients = new ArrayList<>();
        Object mealObj = meal.getMeals().get(0);

        try {
            for (int i = 1; i <= 20; i++) {
                String methodIngredientName = "getStrIngredient" + i;
                String methodMeasureName = "getStrMeasure" + i;
                Method methodIngredient = mealObj.getClass().getMethod(methodIngredientName);
                Method methodMeasure = mealObj.getClass().getMethod(methodMeasureName);
                String ingredientName = (String) methodIngredient.invoke(mealObj);
                String measureName = (String) methodMeasure.invoke(mealObj);
                String ingredientImage = repository.getIngredientImageUrl(ingredientName);

                if (ingredientName != null && !ingredientName.isEmpty()) {
                    ingredients.add(new IngredientUiModel(ingredientName, ingredientImage, measureName));
                }
            }

            view.showIngredients(ingredients);
        } catch (Exception e) {
            e.printStackTrace();
            view.showError(R.string.failure_loading);
        }
    }

    private void getMealDetails(MealResponse mealResponse) {
        MealDto meal = mealResponse.getMeals().get(0);
        MealDetailsUiModel model = new MealDetailsUiModel(meal.getStrMeal(), meal.getStrMealThumb(), meal.getStrCategory(), meal.getStrArea(), meal.getIdMeal());

        view.showMealDetails(model);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}