package com.example.yummy_food_planner.presentation.mealplan.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.mealplan.model.MealPlanUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.GlideImageLoader;

import java.util.List;


public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanAdapter.MealPlanViewHolder> {

    private List<MealPlanUiModel> planMeals;
    public onItemClickListener listener;

    public MealPlanAdapter(){
        listener = null;
    }

    public void setData(List<MealPlanUiModel> meals) {
        planMeals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item,parent,false);
        return new MealPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanViewHolder holder, int position) {
        MealPlanUiModel meal = planMeals.get(position);

        holder.mealName.setText(meal.getName());
        GlideImageLoader.load(holder.itemView.getContext(), meal.getImageUrl(), holder.mealImage);

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> {
                listener.onItemClick(meal.getId(), v);
            });

            holder.deleteIcon.setOnClickListener(v -> {
                listener.onDeleteIconClick(meal.getId(), meal.getDateMillis());
            });
        }
    }

    @Override
    public int getItemCount() {
        return planMeals.size();
    }

    public static class MealPlanViewHolder extends RecyclerView.ViewHolder{

        ImageView mealImage,deleteIcon;
        TextView mealName;
        public MealPlanViewHolder(View view){
            super(view);
            mealImage = view.findViewById(R.id.ivMealImagePlan);
            deleteIcon = view.findViewById(R.id.deleteBtn);
            mealName = view.findViewById(R.id.tvMealNamePlan);
        }
    }
    public interface onItemClickListener {
        void onItemClick(String id, View v);

        void onDeleteIconClick(String id, long date);
    }
}