package com.example.yummy_food_planner.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.model.Meal;
import com.example.yummy_food_planner.utils.GlideImageLoader;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;

    public MealAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.mealName.setText(meal.getName());
        GlideImageLoader.load(holder.itemView.getContext(), meal.getImageUrl(), holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;

        public MealViewHolder(View view) {
            super(view);
            mealImage = view.findViewById(R.id.ivMealImage);
            mealName = view.findViewById(R.id.tvMealName);
        }
    }
}