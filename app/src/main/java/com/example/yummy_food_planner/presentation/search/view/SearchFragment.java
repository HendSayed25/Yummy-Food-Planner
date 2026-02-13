package com.example.yummy_food_planner.presentation.search.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.search.presenter.SearchPresenter;
import com.example.yummy_food_planner.presentation.search.presenter.SearchPresenterImp;
import com.example.yummy_food_planner.presentation.search.utils.Filter;
import com.example.yummy_food_planner.presentation.search.utils.RecyclerListType;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.CustomSnackBar;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SearchFragment extends Fragment implements SearchViews {

    private TextInputEditText etSearch;
    private Chip chipCategory, chipIngredient, chipCountry;
    private RecyclerView searchResultRecycler;
    private SearchMealAdapter searchAdapter;
    private ProgressBar loading;
    private View noInternetLayout;
    private SearchPresenter presenter;
    private AppCompatButton retryBtn;
    private LottieAnimationView emptyStateAnimation;
    private TextView emptyStateText ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etSearch = view.findViewById(R.id.etSearch);
        chipCategory = view.findViewById(R.id.chipCategory);
        chipIngredient = view.findViewById(R.id.chipIngredient);
        chipCountry = view.findViewById(R.id.chipCountry);
        searchResultRecycler = view.findViewById(R.id.searchResultRecycler);
        loading = view.findViewById(R.id.loadingSearch);
        noInternetLayout = view.findViewById(R.id.noInternetLayoutSearch);
        retryBtn = noInternetLayout.findViewById(R.id.retryButton);
        emptyStateAnimation = view.findViewById(R.id.emptyStateAnimation);
        emptyStateText = view.findViewById(R.id.emptyStateText);
        presenter = new SearchPresenterImp(this, getContext());

        chipCategory.setChecked(true);
        presenter.getAllCategories();

        setupRecyclerView();

        setupChips();

        searchForMealByName();

        retryBtn.setOnClickListener(v -> {
            noInternetLayout.setVisibility(GONE);

            if (NetworkCheck.isNetworkAvailable(requireContext())) {
                if (chipCategory.isChecked()) {
                    presenter.getAllCategories();
                } else if (chipCountry.isChecked()) {
                    presenter.getAllCountries();
                } else {
                    presenter.getAllIngredients();
                }
            } else {
                noInternetLayout.setVisibility(VISIBLE);
            }
        });

        searchAdapter.listener = (data, v) -> {
            if (searchAdapter.getType() == RecyclerListType.CATEGORY) FilterByChips(data);
            else navigateToMealsDetails(data, v);
        };
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        searchResultRecycler.setLayoutManager(gridLayoutManager);

        searchAdapter = new SearchMealAdapter();
        searchAdapter.setData(Collections.emptyList());
        searchResultRecycler.setAdapter(searchAdapter);
    }

    private void FilterByChips(String name) {
        if (chipCategory.isChecked()) {
            presenter.getMealsByCategory(name);
            presenter.setFilter(Filter.CATEGORY, name);
        } else if (chipCountry.isChecked()) {
            presenter.getMealsByCountry(name);
            presenter.setFilter(Filter.COUNTRY, name);
        } else {
            presenter.getMealsByIngredient(name);
            presenter.setFilter(Filter.INGREDIENT, name);
        }
    }

    private void navigateToMealsDetails(String mealId, View v) {
        SearchFragmentDirections.ActionSearchFragmentToMealDetailsFragment action = SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(mealId);
        Navigation.findNavController(v).navigate(action);
    }

    private void setupChips() {
        chipCategory.setOnClickListener(v -> {
            chipCategory.setChecked(true);
            chipIngredient.setChecked(false);
            chipCountry.setChecked(false);
            presenter.getAllCategories();
        });

        chipIngredient.setOnClickListener(v -> {
            chipCategory.setChecked(false);
            chipIngredient.setChecked(true);
            chipCountry.setChecked(false);
            presenter.getAllIngredients();
        });

        chipCountry.setOnClickListener(v -> {
            chipCategory.setChecked(false);
            chipIngredient.setChecked(false);
            chipCountry.setChecked(true);
            presenter.getAllCountries();
        });
    }

    private void searchForMealByName() {

        Observable<String> searchObservable = Observable.create(emitter -> {
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emitter.onNext(s.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };

            etSearch.addTextChangedListener(watcher);

            emitter.setCancellable(() -> etSearch.removeTextChangedListener(watcher));
        });

        presenter.getSearchResult(searchObservable);
    }

    @Override
    public void showMeals(List<MealUiModel> meals, RecyclerListType type) {
        if(meals.isEmpty()) {
            emptyStateAnimation.setVisibility(View.VISIBLE);
            searchResultRecycler.setVisibility(View.GONE);
            emptyStateText.setVisibility(VISIBLE);
        } else {
            emptyStateAnimation.setVisibility(View.GONE);
            searchResultRecycler.setVisibility(View.VISIBLE);
            emptyStateText.setVisibility(GONE);
            searchAdapter.setData(meals);
            searchAdapter.setType(type);
        }
    }

    @Override
    public void showViews() {
        searchResultRecycler.setVisibility(VISIBLE);
    }

    @Override
    public void hideViews() {
        searchResultRecycler.setVisibility(GONE);
    }

    @Override
    public void showLoading() {
        loading.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(GONE);
    }

    @Override
    public void showError(int messageId) {
        CustomSnackBar.showSnackBar(requireView(), getContext().getString(messageId), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void noInternet() {
        noInternetLayout.setVisibility(VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }
}