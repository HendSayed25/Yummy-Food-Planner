package com.example.yummy_food_planner.presentation.mealplan.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.example.yummy_food_planner.presentation.shared.utils.CalenderUtils.getSelectedDateInMillis;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.favorite.view.FavoriteFragmentDirections;
import com.example.yummy_food_planner.presentation.mealplan.model.MealPlanUiModel;
import com.example.yummy_food_planner.presentation.mealplan.presenter.MealPlanPresenter;
import com.example.yummy_food_planner.presentation.mealplan.presenter.MealPlanPresenterImp;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.CalenderUtils;
import com.example.yummy_food_planner.presentation.shared.utils.CustomSnackBar;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealPlanFragment extends Fragment implements MealPlanView {
    private MealPlanAdapter adapter;
    private RecyclerView recyclerView;
    private CalendarView calendar;
    private LinearLayout emptyList;
    private MealPlanPresenter presenter;
    private Long dateInMillis;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.plansRecycler);
        emptyList = view.findViewById(R.id.empty_listPlan);
        calendar = view.findViewById(R.id.calendarView);
        adapter = new MealPlanAdapter();
        presenter = new MealPlanPresenterImp(getContext(), this);

        // to get the meals of selected day immediately
        calendar.post(() -> {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            long todayMillis = getSelectedDateInMillis(year, month, day);
            presenter.getPlannedMealsByData(todayMillis);
        });


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateInMillis = getSelectedDateInMillis(year, month, dayOfMonth);
                presenter.getPlannedMealsByData(dateInMillis);
            }
        });

        adapter.listener = new MealPlanAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String id, View v) {
                if (NetworkCheck.isNetworkAvailable(getContext())) {
                    navigateToMealDetails(id, v);
                } else {
                    CustomSnackBar.showSnackBar(requireView(), getString(R.string.no_internet_fav), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
                }
            }

            @Override
            public void onDeleteIconClick(String id, long date) {
                presenter.deleteMealFromPlan(id, date);
            }
        };
    }

    private void navigateToMealDetails(String id, View view) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action = FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(id);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void showMeals(List<MealPlanUiModel> meals) {
        adapter.setData(meals);
        recyclerView.setAdapter(adapter);

        if (meals.isEmpty()) {
            emptyList.setVisibility(VISIBLE);
        } else {
            emptyList.setVisibility(GONE);
        }
    }

    @Override
    public void showMessage(int messageId) {
        CustomSnackBar.showSnackBar(requireView(), getString(messageId), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}