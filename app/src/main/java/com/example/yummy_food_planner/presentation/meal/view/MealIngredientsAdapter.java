package com.example.yummy_food_planner.presentation.meal.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.meal.model.IngredientUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.GlideImageLoader;

import java.util.List;

public class MealIngredientsAdapter extends RecyclerView.Adapter<MealIngredientsAdapter.MealIngredientViewHolder> {

    private List<IngredientUiModel> ingredients;

    public void setData(List<IngredientUiModel> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new MealIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealIngredientViewHolder holder, int position) {
        IngredientUiModel ingredient = ingredients.get(position);

        holder.ingredientName.setText(ingredient.getName());
        holder.ingredientMeasure.setText(ingredient.getMeasure());
        GlideImageLoader.load(holder.itemView.getContext(), ingredient.getImageUrl(), holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class MealIngredientViewHolder extends RecyclerView.ViewHolder {

        ImageView ingredientImage;
        TextView ingredientName, ingredientMeasure;

        public MealIngredientViewHolder(View view) {
            super(view);
            ingredientImage = view.findViewById(R.id.ingredientImage);
            ingredientName = view.findViewById(R.id.ingredientName);
            ingredientMeasure = view.findViewById(R.id.ingredientMeasure);
        }
    }
}