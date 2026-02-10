package com.example.yummy_food_planner.presentation.favorite.view;

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

public class FavMealAdapter extends RecyclerView.Adapter<FavMealAdapter.FavMealViewHolder> {

    private List<MealUiModel> favMealUiModels;
    public onItemClickListener listener;

    public FavMealAdapter() {
        listener = null;
    }

    public void setData(List<MealUiModel> meals) {
        this.favMealUiModels = meals;
        notifyDataSetChanged();
    }

    public static class FavMealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage, favIcon;
        TextView mealName;

        public FavMealViewHolder(View view) {
            super(view);
            mealImage = view.findViewById(R.id.ivMealImageFav);
            mealName = view.findViewById(R.id.tvMealNameFav);
            favIcon = view.findViewById(R.id.favBtn);
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
        MealUiModel favMealUiModel = favMealUiModels.get(position);

        holder.mealName.setText(favMealUiModel.getName());
        GlideImageLoader.load(holder.itemView.getContext(), favMealUiModel.getImageUrl(), holder.mealImage);

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> {
                listener.onItemClick(favMealUiModel.getId(), v);
            });

            holder.favIcon.setOnClickListener(v -> {
                listener.onFavIconClick(favMealUiModel.getId());
            });
        }
    }

    @Override
    public int getItemCount() {
        return favMealUiModels.size();
    }

    public interface onItemClickListener {
        void onItemClick(String id, View v);

        void onFavIconClick(String id);
    }
}