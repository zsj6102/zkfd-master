package com.example.jingbin.zkfudou.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;


import com.example.jingbin.zkfudou.R;
import com.youth.banner.loader.ImageLoader;

public class MyImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(imageView.getContext())
                .load(path)
                .transform(new RoundedCorners( 10))
                .into(imageView);

    }
}
