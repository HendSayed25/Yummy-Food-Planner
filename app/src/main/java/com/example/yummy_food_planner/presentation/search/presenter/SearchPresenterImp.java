package com.example.yummy_food_planner.presentation.search.presenter;

import static com.example.yummy_food_planner.presentation.search.utils.FlagsUtil.getFlagUrl;
import static com.example.yummy_food_planner.presentation.shared.mapper.Mapper.mapToUiList;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.data.meals.datasource.remote.repository.MealRepository;
import com.example.yummy_food_planner.data.meals.datasource.remote.repository.MealsRepositoryImp;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.FilterMealResponse;
import com.example.yummy_food_planner.data.meals.datasource.remote.response.MealResponse;
import com.example.yummy_food_planner.data.model.MealDto;
import com.example.yummy_food_planner.presentation.search.utils.Filter;
import com.example.yummy_food_planner.presentation.search.view.SearchViews;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {

    private MealRepository mealRepository;
    private SearchViews view;
    private Context context;
    private CompositeDisposable compositeDisposable;
    private Filter selectedFilterType = Filter.CATEGORY;
    private String selectedValue = "";
    private List<MealUiModel> originalList;


    public SearchPresenterImp(SearchViews view, Context context) {
        this.view = view;
        this.context = context;
        mealRepository = new MealsRepositoryImp();
        compositeDisposable = new CompositeDisposable();
        originalList = new ArrayList<>();
    }

    @Override
    public void setFilter(Filter type, String value) {
        selectedFilterType = type;
        selectedValue = value;
    }

    @Override
    public void getMealsByCountry(String c) {
        executeRequest(
                mealRepository.getMealsByCountry(c),
                R.string.no_meals_found_for_this_country);
    }

    @Override
    public void getMealsByCategory(String c) {
        executeRequest(
                mealRepository.getMealsByCategory(c),
                R.string.no_meals_found_for_this_category);
    }

    @Override
    public void getMealsByIngredient(String i) {
        executeRequest(
                mealRepository.getMealsByIngredient(i),
                R.string.no_meals_found_for_this_ingredient);
    }

    @Override
    public void getSearchResult(Observable<String> searchObservable) {

        compositeDisposable.add(
                searchObservable
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .switchMap(text ->
                                mealRepository.getMealByName(text)
                                        .subscribeOn(Schedulers.io())
                                        .map(response -> new Pair<>(text, response))
                        )
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pair -> {

                                    String searchText = pair.first;
                                    MealResponse response = pair.second;

                                    if (searchText.isEmpty()) {
                                        view.showMeals(originalList);
                                        return;
                                    }

                                    List<MealDto> filtered = getList(response.getMeals(), searchText);
                                    view.showMeals(mapToUiList(filtered,
                                            meal -> new MealUiModel(meal.getStrMeal(), meal.getStrMealThumb())));

                                },
                                error -> {
                                    view.showError(R.string.no_meals_found_with_this_name);
                                    Log.e("TAG", error.getMessage());
                                }
                        )
        );
    }

    private List<MealDto> getList(List<MealDto> response, String searchText) {
        return response.stream()
                .filter(meal -> {

                    boolean matchesSearch = meal.getStrMeal().toLowerCase().contains(searchText.toLowerCase());

                    boolean matchesChip = true;

                    switch (selectedFilterType) {
                        case CATEGORY:
                            matchesChip = meal.getStrCategory().equals(selectedValue);
                            break;

                        case INGREDIENT:
                            matchesChip = meal.getStrIngredient1().equals(selectedValue);
                            break;

                        case COUNTRY:
                            matchesChip = meal.getStrArea().equals(selectedValue);
                            break;
                    }

                    return matchesSearch && matchesChip;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void getAllCountries() {

        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.noInternet();
            return;
        }

        view.hideViews();
        view.showLoading();

        compositeDisposable.add(
                mealRepository.getAllCountries("list")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    view.hideLoading();
                                    view.showViews();
                                    List<MealUiModel> meals = mapToUiList(
                                            response.getMeals(),
                                            country -> new MealUiModel(
                                                    country.getAreaName(),
                                                    getFlagUrl(country.getAreaName())
                                            )
                                    );
                                    view.showMeals(meals);
                                    originalList = meals;
                                },
                                error -> {
                                    view.hideLoading();
                                    view.showError(R.string.no_countries_found);
                                    Log.e("ERROR", error.getMessage());
                                }
                        )
        );
    }

    @Override
    public void getAllCategories() {
        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.noInternet();
            return;
        }

        view.hideViews();
        view.showLoading();

        compositeDisposable.add(
                mealRepository.getAllCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    view.hideLoading();
                                    view.showViews();
                                    List<MealUiModel> meals = mapToUiList(
                                            response.getCategories(),
                                            category -> new MealUiModel(
                                                    category.getName(),
                                                    category.getImage()
                                            )
                                    );
                                    view.showMeals(meals);
                                    originalList = meals;
                                },
                                error -> {
                                    view.hideLoading();
                                    view.showError(R.string.no_categories_found);
                                    Log.e("ERROR", error.getMessage());
                                }
                        )
        );

    }

    @Override
    public void getAllIngredients() {
        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.noInternet();
            return;
        }

        view.hideViews();
        view.showLoading();

        compositeDisposable.add(
                mealRepository.getAllIngredients("list")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    view.hideLoading();
                                    view.showViews();
                                    view.showMeals(
                                            mapToUiList(
                                                    response.getMeals(),
                                                    ingredient -> new MealUiModel(
                                                            ingredient.getName(),
                                                            ingredient.getThumb()
                                                    )
                                            )
                                    );
                                },
                                error -> {
                                    view.hideLoading();
                                    view.showError(R.string.no_ingredients_found);
                                    Log.e("ERROR", error.getMessage());
                                }
                        )
        );
    }

    private void executeRequest(
            Single<FilterMealResponse> observable,
            int errorMessage
    ) {
        if (!NetworkCheck.isNetworkAvailable(context)) {
            view.noInternet();
            return;
        }

        view.hideViews();
        view.showLoading();

        compositeDisposable.add(
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    view.hideLoading();
                                    view.showViews();
                                    List<MealUiModel> meals = mapToUiList(
                                            response.getMeals(),
                                            meal -> new MealUiModel(
                                                    meal.getName(),
                                                    meal.getImage()
                                            )
                                    );
                                    view.showMeals(meals);
                                    originalList = meals;
                                },
                                error -> {
                                    view.hideLoading();
                                    view.showError(errorMessage);
                                    Log.e("ERROR", error.getMessage());
                                }
                        )
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}