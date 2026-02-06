package com.example.yummy_food_planner.data.network;

import com.example.yummy_food_planner.data.meals.datasource.remote.MealService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Retrofit retrofit;
    private static MealService mealService;
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    private NetworkClient() {}

    public static MealService getInstance() {
        if (mealService == null) {
            synchronized (NetworkClient.class) {
                mealService = getRetrofitInstance().create(MealService.class);
            }
        }
        return mealService;
    }

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            synchronized (NetworkClient.class) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build();

            }
        }
        return retrofit;
    }
}