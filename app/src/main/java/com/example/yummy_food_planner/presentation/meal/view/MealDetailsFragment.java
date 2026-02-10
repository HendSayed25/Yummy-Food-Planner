package com.example.yummy_food_planner.presentation.meal.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.meal.model.IngredientUiModel;
import com.example.yummy_food_planner.presentation.meal.model.MealDetailsUiModel;
import com.example.yummy_food_planner.presentation.meal.presenter.MealDetailsPresenter;
import com.example.yummy_food_planner.presentation.meal.presenter.MealDetailsPresenterImp;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.CustomSnackBar;
import com.example.yummy_food_planner.presentation.shared.utils.GlideImageLoader;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private RecyclerView recyclerView;
    private MealIngredientsAdapter adapter;
    private ImageView mealImage, addToFav, addToCalender;
    private TextView mealName, mealCategory, mealCountry, mealInstructions, youtubeSectionTitle, ingredientTitle, instructionTitle;
    private View noInternetLayout;
    private ProgressBar loading;
    private MealDetailsPresenter presenter;
    private CardView mealCard;
    private YouTubePlayerView playerView;
    private MealDetailsUiModel currentMeal;
    private boolean isFavorite = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.playerView);
        recyclerView = view.findViewById(R.id.ingredientsRecycler);
        mealImage = view.findViewById(R.id.ivMealImageDetails);
        mealName = view.findViewById(R.id.tvMealNameDetails);
        mealCategory = view.findViewById(R.id.tvMealCategory);
        mealCountry = view.findViewById(R.id.tvMealCountyr);
        mealInstructions = view.findViewById(R.id.tvInstructions);
        noInternetLayout = view.findViewById(R.id.noInternetLayoutMealDetails);
        loading = view.findViewById(R.id.loadingMealDetails);
        mealCard = view.findViewById(R.id.mealDetailsCard);
        addToFav = mealCard.findViewById(R.id.icAddToFav);
        addToCalender = mealCard.findViewById(R.id.icAddToCalender);
        ingredientTitle = view.findViewById(R.id.tvIngredients);
        instructionTitle = view.findViewById(R.id.tvInstructionsTitle);
        youtubeSectionTitle = view.findViewById(R.id.tvYoutubeTitle);

        adapter = new MealIngredientsAdapter();
        presenter = new MealDetailsPresenterImp(getContext(), this);

        if (getArguments() != null) {
            String mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
            presenter.getMealDetailsById(mealId);
            presenter.checkIfFavorite(mealId, "");
        }
    }

    @Override
    public void showIngredients(List<IngredientUiModel> ingredients) {
        adapter.setData(ingredients);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMealDetails(MealDetailsUiModel meal) {
        currentMeal = meal;
        mealName.setText(meal.getName());
        mealCountry.setText(meal.getCountry());
        mealCategory.append(meal.getCategory() + ", ");
        GlideImageLoader.load(requireContext(), meal.getMealImageUrl(), mealImage);

        addToFav.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            updateHeartIcon(isFavorite);

            if (isFavorite) {
                presenter.addToFavorite(new MealUiModel(currentMeal.getName(), currentMeal.getMealImageUrl(), currentMeal.getId()));
            } else {
                presenter.removeFromFavorite(new MealUiModel(currentMeal.getName(), currentMeal.getMealImageUrl(), currentMeal.getId()));
            }
        });

        addToCalender.setOnClickListener(v -> {
            presenter.addToCalender(new MealUiModel(meal.getName(), meal.getMealImageUrl(), meal.getId()));
        });
    }

    @Override
    public void showInstructions(String instructions) {
        mealInstructions.setText(instructions);
    }

    @Override
    public void showMealVideo(String videoId) {
        playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }

    @Override
    public void noInternet() {
        noInternetLayout.setVisibility(VISIBLE);
    }

    @Override
    public void showViews() {
        mealCard.setVisibility(VISIBLE);
        mealInstructions.setVisibility(VISIBLE);
        playerView.setVisibility(VISIBLE);
        recyclerView.setVisibility(VISIBLE);
        ingredientTitle.setVisibility(VISIBLE);
        instructionTitle.setVisibility(VISIBLE);
        youtubeSectionTitle.setVisibility(VISIBLE);
    }

    @Override
    public void hideViews() {
        mealCard.setVisibility(GONE);
        mealInstructions.setVisibility(GONE);
        playerView.setVisibility(GONE);
        recyclerView.setVisibility(GONE);
        ingredientTitle.setVisibility(GONE);
        instructionTitle.setVisibility(GONE);
        youtubeSectionTitle.setVisibility(GONE);
    }

    @Override
    public void showError(int messageId) {
        CustomSnackBar.showSnackBar(requireView(), getString(messageId), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void showLoading() {
        loading.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(GONE);
    }

    @Override
    public void addedToFav() {
        addToFav.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_red_heart));
    }

    @Override
    public void removeFromFav() {
        addToFav.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_white_heart)); ////////////// white heart
    }

    private void updateHeartIcon(boolean isFav) {
        if (isFav) {
            addToFav.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_red_heart));
        } else {
            addToFav.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_white_heart));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}