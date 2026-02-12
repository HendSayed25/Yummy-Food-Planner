package com.example.yummy_food_planner.presentation.meal.presenter;

import android.content.Context;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealRepository;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealsRepositoryImp;
import com.example.yummy_food_planner.data.model.dto.MealDto;
import com.example.yummy_food_planner.data.model.entitiy.Meal;
import com.example.yummy_food_planner.data.model.entitiy.Plan;
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
    private AuthRepository authRepository;
    private CompositeDisposable compositeDisposable;
    private Context context;
    private MealDetailsView view;

    public MealDetailsPresenterImp(Context context, MealDetailsView view) {
        this.context = context;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
        repository = new MealsRepositoryImp(context);
        authRepository = new AuthRepositoryImp(context);
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
                authRepository.getUserId()
                        .subscribeOn(Schedulers.io())
                        .flatMapCompletable(userId ->
                                repository.addMealToFavorite(new Meal(meal.getId(), userId, meal.getName(), meal.getImageUrl())))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.addedToFav(),
                                error -> view.showError(R.string.failure_loading)
                        )
        );
    }

    public void deleteFromFavorite(MealUiModel meal) {
        compositeDisposable.add(
                authRepository.getUserId()
                        .subscribeOn(Schedulers.io())
                        .flatMapCompletable(userId ->
                                repository.deleteMealFromFavorite(meal.getId(), userId)
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.deleteFromFav(),
                                error -> view.showError(R.string.failure_loading)
                        )
        );
    }

    @Override
    public void addToPlan(MealUiModel meal, Long date) {
        compositeDisposable.add(
                authRepository.getUserId()
                        .subscribeOn(Schedulers.io())
                        .flatMapCompletable(userId ->
                                repository.addMealToPlan(new Plan(userId, meal.getId(), date, meal.getName(), meal.getImageUrl())))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showAddedToMealPlanIcon(),
                                error -> view.showError(R.string.failure_loading)
                        )
        );
    }

    @Override
    public void checkIfFavorite(String mealId) {
        compositeDisposable.add(
                authRepository.getUserId()
                        .subscribeOn(Schedulers.io())
                        .flatMap(userId -> repository.isMealFavorite(mealId, userId))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isFav -> {
                                    if (isFav) {
                                        view.addedToFav();
                                    } else {
                                        view.deleteFromFav();
                                    }
                                },
                                throwable -> view.showError(R.string.failure_loading)
                        )
        );
    }

    @Override
    public void checkIfPlanned(String mealId) {
        compositeDisposable.add(
                authRepository.getUserId()
                        .subscribeOn(Schedulers.io())
                        .flatMap(userId -> repository.isMealPlaned(mealId, userId))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isPlan -> {
                                    if (isPlan) {
                                        view.showAddedToMealPlanIcon();
                                    } else {
                                        view.showAddToPlanIcon();
                                    }
                                },
                                throwable -> view.showError(R.string.failure_loading)
                        )
        );

    }

    @Override
    public void isUserGuest(Runnable actionIfNotGuest) {
        compositeDisposable.add(
                authRepository.isUserGuest().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                state -> {
                                    if (state) view.showGuestDialog();
                                    else actionIfNotGuest.run();
                                }
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