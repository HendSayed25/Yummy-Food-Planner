package com.example.yummy_food_planner.presentation.favorite.presenter;

import static com.example.yummy_food_planner.presentation.shared.mapper.Mapper.mapToUiList;

import android.content.Context;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealRepository;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealsRepositoryImp;
import com.example.yummy_food_planner.presentation.favorite.view.FavoriteView;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritePresenterImp implements FavoritePresenter {

    private MealRepository mealRepository;
    private FavoriteView view;
    private CompositeDisposable compositeDisposable;

    public FavoritePresenterImp(Context context, FavoriteView view) {
        this.view = view;
        mealRepository = new MealsRepositoryImp(context);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void showAllFavorites() {
        compositeDisposable.add(
                mealRepository.getAllFavoriteMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> view.showFavMeals(mapToUiList(meals, m -> new MealUiModel(m.getName(), m.getMealThumb(), m.getId()))),
                                error -> view.showMessage(R.string.failure_loading)
                        )
        );
    }

    @Override
    public void removeFromFavorite(String mealId, String userId) {
        compositeDisposable.add(
                mealRepository.deleteMealFromFavorite(mealId, userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        () -> {
                        },
                        error -> view.showMessage(R.string.failure_loading)
                )
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}