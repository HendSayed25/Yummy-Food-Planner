package com.example.yummy_food_planner.data.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public static String fromList(List<String> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<String> toList(String data) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }
}