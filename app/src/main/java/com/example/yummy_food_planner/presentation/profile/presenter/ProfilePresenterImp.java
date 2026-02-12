package com.example.yummy_food_planner.presentation.profile.presenter;

import android.content.Context;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealRepository;
import com.example.yummy_food_planner.data.meals.datasource.repository.MealsRepositoryImp;
import com.example.yummy_food_planner.presentation.profile.view.ProfileView;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImp implements ProfilePresenter {

    private CompositeDisposable compositeDisposable;
    private AuthRepository repository;
    private MealRepository mealRepository;
    private ProfileView view;
    private Context context;

    public ProfilePresenterImp(Context context, ProfileView view) {
        this.view = view;
        this.context = context;
        compositeDisposable = new CompositeDisposable();
        repository = new AuthRepositoryImp(context);
        mealRepository = new MealsRepositoryImp(context);
    }

    @Override
    public void logOut() {
        compositeDisposable.add(
                repository.logout().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.logOut(),
                                error -> view.showError(R.string.something_went_wrong)
                        )
        );
    }

    @Override
    public void getUserData() {
        compositeDisposable.add(
                repository.getUserData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> view.showUserData(user.first, user.second),
                                error -> view.showError(R.string.something_went_wrong)
                        )
        );
    }

    @Override
    public void isUserGuest() {
        compositeDisposable.add(
                repository.isUserGuest().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isGuest -> {
                                    if (isGuest) {
                                        view.userIsGuest();
                                        view.showUserData("Guest", "guest@gmail.com");
                                    } else view.userNotAGuest();
                                }
                        )
        );
    }

    @Override
    public void syncUserData() {

        if(!NetworkCheck.isNetworkAvailable(context)){
            view.noInternet();
            return;
        }

        compositeDisposable.add(
                mealRepository.syncUserData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.syncDataSuccessfully(),
                                error -> {
                                    view.showError(R.string.something_went_wrong);
                                }
                        )
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}