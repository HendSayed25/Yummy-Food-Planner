package com.example.yummy_food_planner.presentation.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.GlideImageLoader;

import java.util.List;

public class HomeMealAdapter extends RecyclerView.Adapter<HomeMealAdapter.MealViewHolder> {

    private List<MealUiModel> mealUiModels;
    public void setData(List<MealUiModel> mealUiModels){
        this.mealUiModels = mealUiModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealUiModel mealUiModel = mealUiModels.get(position);
        GlideImageLoader.load(holder.itemView.getContext(),mealUiModel.getImageUrl(),holder.mealImage);
        holder.mealName.setText(mealUiModel.getName());
    }

    @Override
    public int getItemCount() {
        return mealUiModels.size();
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