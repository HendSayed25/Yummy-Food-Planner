package com.example.yummy_food_planner.presentation.welcome.onboarding.presenter;

import android.content.Context;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepository;
import com.example.yummy_food_planner.data.authentication.datasource.repository.AuthRepositoryImp;
import com.example.yummy_food_planner.presentation.welcome.onboarding.view.OnboardingView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OnboardingPresenterImp implements OnboardingPresenter {

    private AuthRepository authRepository;
    private CompositeDisposable compositeDisposable;
    private OnboardingView view;

    public OnboardingPresenterImp(Context context, OnboardingView view) {
        authRepository = new AuthRepositoryImp(context);
        compositeDisposable = new CompositeDisposable();
        this.view = view;
    }

    @Override
    public void setNotFirstTime() {
        compositeDisposable.add(
                authRepository.setIsNotFirstTime().
                        subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.goToSignInScreen(),
                                error -> view.showErrorMessage(R.string.something_went_wrong)
                        )
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}