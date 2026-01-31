package com.example.yummy_food_planner.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.home.Meal;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private RecyclerView favRecycler;
    private FavMealAdapter favMealAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favRecycler = view.findViewById(R.id.favoriteRecycler);
        favMealAdapter = new FavMealAdapter(getMeals());
        favRecycler.setAdapter(favMealAdapter);
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
}