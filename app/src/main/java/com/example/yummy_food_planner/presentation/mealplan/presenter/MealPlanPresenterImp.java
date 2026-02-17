package com.example.yummy_food_planner.presentation.mealplan.presenter;

import static com.example.yummy_food_planner.presentation.shared.mapper.Mapper.mapToUiList;

import android.content.Context;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealRepository;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealsRepositoryImp;
import com.example.yummy_food_planner.presentation.mealplan.model.MealPlanUiModel;
import com.example.yummy_food_planner.presentation.mealplan.view.MealPlanView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPlanPresenterImp implements MealPlanPresenter {

    private final MealRepository mealRepository;
    private final AuthRepository authRepository;
    private final CompositeDisposable compositeDisposable;
    private final MealPlanView view;

    public MealPlanPresenterImp(Context context, MealPlanView view) {
        this.view = view;
        mealRepository = new MealsRepositoryImp(context);
        authRepository = new AuthRepositoryImp(context);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getPlannedMealsByData(Long date) {
        compositeDisposable.add(
                authRepository.getUserId().subscribeOn(Schedulers.io())
                        .flatMapObservable(userId -> mealRepository.getPlanedMealsByDate(date, userId))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> view.showMeals(mapToUiList(meals, m -> new MealPlanUiModel(m.getName(), m.getMealThumb(), m.getMealId(),m.getDate()))),
                                error -> view.showMessage(R.string.failure_loading)
                        )
        );

    }

    @Override
    public void deleteMealFromPlan(String mealId, Long date) {
        compositeDisposable.add(
                authRepository.getUserId().subscribeOn(Schedulers.io())
                        .flatMapCompletable(userId -> mealRepository.deleteMealFromPlan(mealId, date, userId))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showMessage(R.string.success_delete_message),
                                error -> view.showMessage(R.string.failure_loading)
                        )
        );

    }

    @Override
    public void isUserGuest(Long date) {
        compositeDisposable.add(
                authRepository.isUserGuest().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                state -> {
                                    if (state) view.showGuestDialog();
                                    else view.userNotAGuest(date);
                                }
                        )
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
