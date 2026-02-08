package com.example.yummy_food_planner.presentation.search.presenter;



import com.example.yummy_food_planner.presentation.search.utils.Filter;

import io.reactivex.rxjava3.core.Observable;

public interface SearchPresenter {
    void getMealsByCountry(String c);
    void getMealsByCategory(String c);
    void getMealsByIngredient(String i);
    void getSearchResult(Observable<String> searchObservable);
    void getAllCountries();
    void getAllCategories();
    void getAllIngredients();
    void setFilter(Filter type, String value);
    void onDestroy();
}