🍽️ Meal Planner App

# Overview
Meal Planner is an Android application that helps users discover, plan, and manage meals. The app provides personalized meal suggestions, allows searching and filtering meals, and helps users organize their favorites and meal plans. It supports both authenticated users and guests.

# Features

1- Home Screen :

- Displays a list of meals.
- Shows "Meal of the Day" suggested by the app.
- Users can explore meals quickly and get daily recommendations.

2- Search Screen :

- Search meals by name.
- Filter meals by: Category , Country ,Ingredient
- Helps users find exactly what they want.

3- Favorites Screen :
- Users can add any meal to their favorites.
- Meals in favorites can be deleted.

4- Meal Plan / Calendar :
- Users can add meals to their personal meal plan/calendar.
- Meals can be removed from the plan.

5- Profile & Authentication
- Users can sign in using: ( Email & Password , Google Sign-In Guest mode )
- Guests can view meals but cannot add meals to favorites or meal plan.
- Logged in user can Syncs his data (favorites and plans) to Firestore to can access them from any device.

# Architecture & Tech Stack

- Architecture: MVP (Model-View-Presenter)

- Reactive Programming: RxJava for asynchronous operations.

- Navigation: Android Navigation Graph

- UI: XML-based layouts

- Backend: Firebase Authentication & Firestore for user management and data syncing

# Screens & Flow

- Home → Suggested meals & daily recommendations.

- Search → Search by name + filter by category/country/ingredient.

- Favorites → Add/remove meals; synced with Firestore.

- Meal Plan / Calendar → Organize meals; synced with Firestore.

- Profile / Auth → Sign in/up; view profile.


# Installation

- Clone the repository:

- git clone <repo_url>
- Open the project in Android Studio.
- Add Firebase configuration (google-services.json) to the app folder.
- Sync Gradle.
- Run the app on an emulator or device.

# Dependencies
- Firebase Authentication
- Firebase Firestore
- RxJava & RxAndroid
- Android Navigation Component
- Gson / Retrofit (if fetching meals from API)
- Lottie (optional, for animations)