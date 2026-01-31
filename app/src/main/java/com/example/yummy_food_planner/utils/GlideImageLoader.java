package com.example.yummy_food_planner.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yummy_food_planner.R;

public class GlideImageLoader {

    private GlideImageLoader() {
    }

    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.ic_logo_24).into(imageView);
    }
}