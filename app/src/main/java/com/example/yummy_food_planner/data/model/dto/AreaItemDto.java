package com.example.yummy_food_planner.data.model.dto;

import com.google.gson.annotations.SerializedName;

public class AreaItemDto {

    @SerializedName("strArea")
    private String areaName;

    public String getAreaName() {
        return areaName;
    }
}