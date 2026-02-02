package com.example.yummy_food_planner.search;

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

public class SearchMealAdapter extends RecyclerView.Adapter<SearchMealAdapter.SearchMealViewHolder> {

    private List<Meal> meals;

    public SearchMealAdapter(List<Meal> meals){
        this.meals = meals;
    }

    @NonNull
    @Override
    public SearchMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new SearchMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMealViewHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.mealName.setText(meal.getName());
        GlideImageLoader.load(holder.itemView.getContext(), meal.getImageUrl(), holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class SearchMealViewHolder extends RecyclerView.ViewHolder{

        ImageView mealImage;
        TextView mealName;
        public SearchMealViewHolder(View view){
            super(view);

            mealImage = view.findViewById(R.id.ivMealImageSearch);
            mealName = view.findViewById(R.id.tvMealNameSearch);
        }
    }
}