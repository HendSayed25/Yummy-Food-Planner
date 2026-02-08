package com.example.yummy_food_planner.presentation.home.presenter;

import static com.example.yummy_food_planner.presentation.shared.mapper.Mapper.mapToUi;
import static com.example.yummy_food_planner.presentation.shared.mapper.Mapper.mapToUiList;

import android.content.Context;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.meals.datasource.remote.repository.MealRepository;
import com.example.yummy_food_planner.data.meals.datasource.remote.repository.MealsRepositoryImp;
import com.example.yummy_food_planner.presentation.home.model.RandomMealUiModel;
import com.example.yummy_food_planner.presentation.home.view.HomeView;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImp implements HomePresenter {

    private MealRepository repository;
    private CompositeDisposable compositeDisposable;
    private HomeView view;
    private Context context;

    public HomePresenterImp(HomeView view, Context context) {
        this.view = view;
        this.context = context;
        repository = new MealsRepositoryImp();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getRandomMeal() {
        if (NetworkCheck.isNetworkAvailable(context)) {
            view.hideViews();
            view.showLoading();

            compositeDisposable.add(
                    repository.getRandomMeal().subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    meal -> {
                                        view.hideLoading();
                                        view.showViews();
                                        view.showRandomMeal(mapToUi(meal.getMeals().get(0),
                                                m -> new RandomMealUiModel(
                                                        m.getStrMeal(),
                                                        m.getStrCategory(),
                                                        m.getStrMealThumb(),
                                                        m.getIdMeal()
                                                )));
                                    },
                                    error -> {
                                        if (error instanceof IOException) {
                                            view.noInternet();
                                        } else {
                                            view.showError(R.string.no_meals_for_today);
                                        }
                                    }
                            )
            );
        } else {
            view.hideLoading();
            view.hideViews();
            view.noInternet();
        }
    }

    @Override
    public void getMealsByLetter(String letter) {
        if (NetworkCheck.isNetworkAvailable(context)) {
            view.hideViews();
            view.showLoading();

            compositeDisposable.add(
                    repository.getMealsByLetter(letter).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    meals -> {
                                        view.hideLoading();
                                        view.showViews();
                                        view.showMeals(mapToUiList(meals.getMeals(),
                                                m -> new MealUiModel(
                                                        m.getStrMeal(),
                                                        m.getStrMealThumb(),
                                                        m.getIdMeal()
                                                )));
                                    },
                                    error -> {
                                        if (error instanceof IOException) {
                                            view.noInternet();
                                        } else {
                                            view.hideLoading();
                                            view.showError(R.string.no_meals_for_today);
                                        }
                                    }
                            )
            );
        } else {
            view.hideLoading();
            view.hideViews();
            view.noInternet();
        }
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}