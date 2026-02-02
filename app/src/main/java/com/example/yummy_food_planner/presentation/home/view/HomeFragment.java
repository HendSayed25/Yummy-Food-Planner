package com.example.yummy_food_planner.presentation.home.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.shared.model.Meal;
import com.example.yummy_food_planner.presentation.shared.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mealRecycler;
    private HomeMealAdapter homeMealAdapter;

    private View mealOfDay;
    private View noInternetLayout;
    private TextView homeTitle1,homeTitle2,homeTitle3;
    private ImageView logoIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealOfDay = view.findViewById(R.id.mealOfDay);
        noInternetLayout = view.findViewById(R.id.noInternetLayout);
        mealRecycler = view.findViewById(R.id.mealsHomeRecycler);
        homeTitle1 = view.findViewById(R.id.title1);
        homeTitle2 = view.findViewById(R.id.home_title2);
        homeTitle3 = view.findViewById(R.id.home_title3);

        if (isNetworkAvailable()) {
            showContent();
        } else {
            showNoInternet();
        }

    }

    private void showNoInternet() {
        noInternetLayout.setVisibility(View.VISIBLE);
        mealOfDay.setVisibility(View.GONE);
        homeTitle1.setVisibility(View.GONE);
        homeTitle2.setVisibility(View.GONE);
        homeTitle3.setVisibility(View.GONE);
        mealRecycler.setVisibility(View.GONE);
    }

    private void showContent() {
        noInternetLayout.setVisibility(View.GONE);
        mealOfDay.setVisibility(View.VISIBLE);
        homeTitle1.setVisibility(View.VISIBLE);
        homeTitle2.setVisibility(View.VISIBLE);
        homeTitle3.setVisibility(View.VISIBLE);
        mealRecycler.setVisibility(View.VISIBLE);

        homeMealAdapter = new HomeMealAdapter(getMeals());
        mealRecycler.setAdapter(homeMealAdapter);

        setRandomMealOfDay();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private List<Meal> getMeals(){
        List<Meal> meals = new ArrayList<>();

        meals.add(new Meal("Spaghetti Carbonara",
                "https://tse1.mm.bing.net/th/id/OIP.3f4uw03GjHN2wa2tSeNc4wHaIu?rs=1&pid=ImgDetMain&o=7&rm=3"));

        meals.add(new Meal("Chicken Tikka",
                "https://images.pexels.com/photos/7593252/pexels-photo-7593252.jpeg?cs=srgb&dl=pexels-ahmad-no-more-7593252.jpg&fm=jpg"));

        meals.add(new Meal( "Greek Salad",
                "https://t4.ftcdn.net/jpg/03/97/74/85/360_F_397748564_dGlYErHROD7bxSDsZFkQODn0asgWfBv8.jpg"));

        return meals;
    }

    private void setRandomMealOfDay(){
        GlideImageLoader.load(requireContext(),"https://tse1.mm.bing.net/th/id/OIP.3f4uw03GjHN2wa2tSeNc4wHaIu?rs=1&pid=ImgDetMain&o=7&rm=3",mealOfDay.findViewById(R.id.ivMealImage));
       TextView t= mealOfDay.findViewById(R.id.tvMealName);
       TextView tt = mealOfDay.findViewById(R.id.tvMealDescription);
       t.setText("Burger");
       tt.setText("Delecious............................................................................................................................");
    }
}