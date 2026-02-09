package com.example.yummy_food_planner.presentation.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.search.utils.RecyclerListType;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.GlideImageLoader;

import java.util.List;

public class SearchMealAdapter extends RecyclerView.Adapter<SearchMealAdapter.SearchMealViewHolder> {

    private List<MealUiModel> mealUiModels;
    public onItemClickListener listener;
    private RecyclerListType type;

    public SearchMealAdapter() {
        listener = null;
        type = RecyclerListType.CATEGORY;
    }

    @NonNull
    @Override
    public SearchMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMealViewHolder holder, int position) {
        MealUiModel mealUiModel = mealUiModels.get(position);

        holder.mealName.setText(mealUiModel.getName());
        GlideImageLoader.load(holder.itemView.getContext(), mealUiModel.getImageUrl(), holder.mealImage);

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> {
                if (type == RecyclerListType.CATEGORY) listener.onItemClick(holder.mealName.getText().toString(),v);
                else listener.onItemClick(mealUiModel.getId(),v);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mealUiModels.size();
    }

    public static class SearchMealViewHolder extends RecyclerView.ViewHolder {

        ImageView mealImage;

        TextView mealName;

        public SearchMealViewHolder(View view) {
            super(view);

            mealImage = view.findViewById(R.id.ivMealImageSearch);
            mealName = view.findViewById(R.id.tvMealNameSearch);
        }

    }

    public RecyclerListType getType() {
        return type;
    }

    public void setType(RecyclerListType type) {
        this.type = type;
    }

    public void setData(List<MealUiModel> mealUiModels) {
        this.mealUiModels = mealUiModels;
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void onItemClick(String data,View v);
    }
}