package com.example.yummy_food_planner.presentation.meal.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.meal.model.IngredientUiModel;
import com.example.yummy_food_planner.presentation.meal.model.MealDetailsUiModel;

import java.util.Arrays;
import java.util.List;

public class MealDetailsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MealIngredientsAdapter adapter;
    private PlayerView playerView;
    private  ExoPlayer player;
    private MediaItem mediaItem ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.playerView);
        recyclerView = view.findViewById(R.id.ingredientsRecycler);
        adapter = new MealIngredientsAdapter();

        player = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(player);

        mediaItem = MediaItem.fromUri(
                "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"
        );

        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();



        List<IngredientUiModel> fakeList = Arrays.asList(
                new IngredientUiModel(
                        "Chicken",
                        "https://www.themealdb.com/images/ingredients/Chicken.png"
                ),
                new IngredientUiModel(
                        "Salt",
                        "https://www.themealdb.com/images/ingredients/Salt.png"
                ),
                new IngredientUiModel(
                        "Tomato",
                        "https://www.themealdb.com/images/ingredients/Tomato.png"
                )
        );

        MealDetailsUiModel fakeMeal =
                new MealDetailsUiModel(
                        "Spaghetti Carbonara",
                        "https://www.themealdb.com/images/media/meals/llcbn01574260722.jpg",

                        "Pasta",
                        "https://www.themealdb.com/images/category/pasta.png",

                        "Italian",
                        "https://flagcdn.com/w320/it.png"
                );

        ImageView mealImage = view.findViewById(R.id.ivMealImageDetails);
        ImageView categoryImage = view.findViewById(R.id.categoryImage);
        ImageView countryImage = view.findViewById(R.id.countryImage);

        TextView name = view.findViewById(R.id.tvMealNameDetails);
        TextView category = view.findViewById(R.id.tvMealCategory);
        TextView country = view.findViewById(R.id.tvMealCountyr);

        name.setText(fakeMeal.getName());
        category.setText(fakeMeal.getCategory());
        country.setText(fakeMeal.getCountry());

        Glide.with(requireContext()).load(fakeMeal.getMealImageUrl()).into(mealImage);
        Glide.with(requireContext()).load(fakeMeal.getCategoryImageUrl()).into(categoryImage);
        Glide.with(requireContext()).load(fakeMeal.getCountryImageUrl()).into(countryImage);


        adapter.setData(fakeList);
        recyclerView.setAdapter(adapter);

        String instructions =
                "1. Boil water in a large pot.\n" +
                        "2. Add pasta and stir occasionally.\n" +
                        "3. Cook for 10 minutes until al dente.\n" +
                        "4. Drain pasta and add sauce.\n" +
                        "5. Serve hot and enjoy!";
        TextView tvInstructions = view.findViewById(R.id.tvInstructions);
        tvInstructions.setText(instructions);

    }
}