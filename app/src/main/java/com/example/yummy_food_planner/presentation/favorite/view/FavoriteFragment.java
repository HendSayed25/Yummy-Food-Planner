package com.example.yummy_food_planner.presentation.favorite.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yummy_food_planner.R;
import com.example.yummy_food_planner.presentation.favorite.presenter.FavoritePresenter;
import com.example.yummy_food_planner.presentation.favorite.presenter.FavoritePresenterImp;
import com.example.yummy_food_planner.presentation.shared.model.MealUiModel;
import com.example.yummy_food_planner.presentation.shared.utils.CustomDialog;
import com.example.yummy_food_planner.presentation.shared.utils.CustomSnackBar;
import com.example.yummy_food_planner.presentation.shared.utils.NetworkCheck;

import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteView {

    private RecyclerView favRecycler;
    private FavMealAdapter favMealAdapter;
    private FavoritePresenter presenter;
    private TextView screenTitle;
    private LinearLayout noFavoritesLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favRecycler = view.findViewById(R.id.favoriteRecycler);
        screenTitle = view.findViewById(R.id.favoriteTitle);
        noFavoritesLayout = view.findViewById(R.id.empty_list);
        favMealAdapter = new FavMealAdapter();
        presenter = new FavoritePresenterImp(getContext(), this);

        presenter.showAllFavorites();

        favMealAdapter.listener = new FavMealAdapter.onItemClickListener() {
            @Override
            public void onItemClick(String id, View v) {
                if (NetworkCheck.isNetworkAvailable(getContext())) {
                    navigateToMealDetails(id, v);
                } else {
                    CustomSnackBar.showSnackBar(requireView(), getString(R.string.no_internet_fav), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
                }
            }

            @Override
            public void onFavIconClick(String id) {
                CustomDialog.showDialog(
                        requireContext(),
                        getString(R.string.delete_item_from_fav),
                        getString(R.string.are_you_sure_you_want_to_delete_this_meal),
                        getString(R.string.delete),
                        getString(R.string.cancel),
                        () -> presenter.deleteFromFavorite(id));
            }
        };
    }

    private void navigateToMealDetails(String id, View view) {
        FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action = FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(id);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void showFavMeals(List<MealUiModel> meals) {
        favMealAdapter.setData(meals);
        favRecycler.setAdapter(favMealAdapter);

        if (meals.isEmpty()) {
            screenTitle.setVisibility(GONE);
            noFavoritesLayout.setVisibility(VISIBLE);
        } else {
            screenTitle.setVisibility(VISIBLE);
            noFavoritesLayout.setVisibility(GONE);
        }
    }

    @Override
    public void showMessage(int messageId) {
        CustomSnackBar.showSnackBar(requireView(), getString(messageId), getResources().getColor(R.color.logo_bg), getResources().getColor(R.color.blue_primary));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }
}