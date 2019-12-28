package com.example.jingbin.zkfudou.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class RoundCorner extends BitmapTransformation {
    private float leftTop;
    private float rightTop;
    private float rightBottom;
    private float leftBottom;
    private String ID = this.getClass().getName()+leftTop+rightTop+rightBottom+rightTop;
    public RoundCorner(Context context, float leftTop, float rightTop, float rightBottom, float leftBottom) {
        this.leftTop = dip2px(context, leftTop);
        this.rightTop = dip2px(context, rightTop);
        this.rightBottom = dip2px(context, rightBottom);
        this.leftBottom = dip2px(context, leftBottom);
    }

    private int dip2px(Context context, float var) {
        float var2 = context.getResources().getDisplayMetrics().density;
        return (int) (var * var2 + 0.5);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        RectF rect = new RectF(0f, 0f, (float) width, (float) height);
        float[] radii = {leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom};
        Path path = new Path();
        path.addRoundRect(rect, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
        return bitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID.getBytes());
    }
}
