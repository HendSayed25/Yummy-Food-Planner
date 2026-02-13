package com.example.yummy_food_planner;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.onboardingFragment1
                    || destination.getId() == R.id.onboardingFragment2
                    || destination.getId() == R.id.onboardingFragment3
                    || destination.getId() == R.id.splashFragment
                    || destination.getId() == R.id.signInFragment
                    || destination.getId() == R.id.signUpFragment) {

                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {

            NavOptions options = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(navController.getGraph().getStartDestinationId(), true)
                    .build();

            int id = item.getItemId();

            if (id == R.id.homeFragment) {
                navController.navigate(R.id.homeFragment, null, options);
                return true;

            } else if (id == R.id.favoriteFragment) {
                navController.navigate(R.id.favoriteFragment, null, options);
                return true;

            } else if (id == R.id.searchFragment) {
                navController.navigate(R.id.searchFragment, null, options);
                return true;

            } else if (id == R.id.profileFragment) {
                navController.navigate(R.id.profileFragment, null, options);
                return true;
            } else if (id==R.id.mealPlanFragment) {
                navController.navigate(R.id.mealPlanFragment,null,options);
            }

            return false;
        });
    }
}