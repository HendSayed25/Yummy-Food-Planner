package com.example.yummy_food_planner.favorite;

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

public class FavMealAdapter extends RecyclerView.Adapter<FavMealAdapter.FavMealViewHolder> {

    private List<Meal> favMeals;

    public FavMealAdapter(List<Meal> favMeals) {
        this.favMeals = favMeals;
    }

    public static class FavMealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;

        public FavMealViewHolder(View view) {
            super(view);
            mealImage = view.findViewById(R.id.ivMealImageFav);
            mealName = view.findViewById(R.id.tvMealNameFav);

        }
    }

    @NonNull
    @Override
    public FavMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new FavMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMealViewHolder holder, int position) {
        Meal favMeal = favMeals.get(position);

        holder.mealName.setText(favMeal.getName());
        GlideImageLoader.load(holder.itemView.getContext(), favMeal.getImageUrl(), holder.mealImage);
    }

    @Override
    public int getItemCount() {
        return favMeals.size();
    }
}