package com.example.yummy_food_planner.presentation.welcome.splash.presenter;

import android.content.Context;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.presentation.welcome.splash.view.SplashView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashPresenterImp implements SplashPresenter {

    private AuthRepository authRepository;
    private CompositeDisposable compositeDisposable;
    private SplashView view;

    public SplashPresenterImp(Context context, SplashView view) {
        authRepository = new AuthRepositoryImp(context);
        compositeDisposable = new CompositeDisposable();
        this.view = view;
    }

    @Override
    public void checkLoginState() {
        compositeDisposable.add(
                authRepository.isFirstTime()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isFirst -> {
                                    if (isFirst) {
                                        view.goToOnboardingScreens();
                                    } else {
                                        checkIfLoggedIn();
                                    }
                                },
                                error -> {
                                    view.showError(R.string.something_went_wrong);
                                }
                        )
        );
    }

    private void checkIfLoggedIn() {
        compositeDisposable.add(
                authRepository.isUserLoggedIn()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isLogged -> {
                                    if (isLogged) {
                                        view.goToHomeScreen();
                                    } else {
                                        view.goToSignInScreen();
                                    }
                                },
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