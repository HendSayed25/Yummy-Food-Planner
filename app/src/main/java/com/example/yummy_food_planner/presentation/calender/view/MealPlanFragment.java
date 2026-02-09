package com.example.yummy_food_planner.presentation.calender.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;

import java.util.ArrayList;
import java.util.List;

public class MealPlanFragment extends Fragment {
    private  MealPlanAdapter adapter;
    private RecyclerView recyclerView;
    private CalendarView calendar;
    private TextView emptyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.plansRecycler);
        emptyList = view.findViewById(R.id.emptyText);
        calendar = view.findViewById(R.id.calendarView);
        adapter = new MealPlanAdapter();

        List<MealUiModel> fakeMeals = new ArrayList<>();
        fakeMeals.add(new MealUiModel(
                "Pasta",
                "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg",
                "1"
        ));

        fakeMeals.add(new MealUiModel(
                "Burger",
                "https://www.themealdb.com/images/media/meals/sypxpx1515365095.jpg",
                "2"
        ));

        fakeMeals.add(new MealUiModel(
                "Pizza",
                "https://www.themealdb.com/images/media/meals/x0lk931587671540.jpg",
                "3"
        ));

        fakeMeals.add(new MealUiModel(
                "Chicken",
                "https://www.themealdb.com/images/media/meals/1529446352.jpg",
                "4"
        ));
        fakeMeals.add(new MealUiModel(
                "Chicken",
                "https://www.themealdb.com/images/media/meals/1529446352.jpg",
                "4"
        ));  fakeMeals.add(new MealUiModel(
                "Chicken",
                "https://www.themealdb.com/images/media/meals/1529446352.jpg",
                "4"
        ));

        adapter.setData(fakeMeals);
        recyclerView.setAdapter(adapter);

    }
}