package com.example.yummy_food_planner.presentation.search.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private TextInputEditText etSearch;
    private Chip chipCategory, chipIngredient, chipCountry;
    private RecyclerView searchResultRecycler;
    private SearchMealAdapter searchAdapter;
    private List<MealUiModel> allMealUiModels;

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

        chipCategory.setChecked(true);

        allMealUiModels = getMealsList();

        setupRecyclerView();

        setupChips();
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        searchResultRecycler.setLayoutManager(gridLayoutManager);

        searchAdapter = new SearchMealAdapter(allMealUiModels);
        searchResultRecycler.setAdapter(searchAdapter);
    }

    private void setupChips() {
        chipCategory.setOnClickListener(v -> {
            chipCategory.setChecked(true);
            chipIngredient.setChecked(false);
            chipCountry.setChecked(false);
        });

        chipIngredient.setOnClickListener(v -> {
            chipCategory.setChecked(false);
            chipIngredient.setChecked(true);
            chipCountry.setChecked(false);
        });

        chipCountry.setOnClickListener(v -> {
            chipCategory.setChecked(false);
            chipIngredient.setChecked(false);
            chipCountry.setChecked(true);
        });
    }

    private List<MealUiModel> getMealsList() {
        List<MealUiModel> mealUiModels = new ArrayList<>();

        mealUiModels.add(new MealUiModel("Margherita Pizza", "https://www.themealdb.com/images/media/meals/x0lk931587671540.jpg"));

        mealUiModels.add(new MealUiModel("Pad Thai", "https://www.themealdb.com/images/media/meals/1529444830.jpg"));

        mealUiModels.add(new MealUiModel("Sushi Roll", "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg"));

        mealUiModels.add(new MealUiModel("Tacos", "https://www.themealdb.com/images/media/meals/tkxquw1628771028.jpg"));

        mealUiModels.add(new MealUiModel("Lasagna", "https://www.themealdb.com/images/media/meals/wtsvxx1511296896.jpg"));

        mealUiModels.add(new MealUiModel("Pancakes", "https://www.themealdb.com/images/media/meals/rwuyqx1511383174.jpg"));

        mealUiModels.add(new MealUiModel("Pad Thai", "https://www.themealdb.com/images/media/meals/1529444830.jpg"));

        mealUiModels.add(new MealUiModel("Sushi Roll", "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg"));

        mealUiModels.add(new MealUiModel("Tacos", "https://www.themealdb.com/images/media/meals/tkxquw1628771028.jpg"));

        return mealUiModels;
    }
}