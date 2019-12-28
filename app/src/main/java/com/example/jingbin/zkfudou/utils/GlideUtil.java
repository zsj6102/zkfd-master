package com.example.jingbin.zkfudou.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.jingbin.zkfudou.R;



/**
 * @author jingbin
 * @date 2019/04/29
 */

public class GlideUtil {

    private static GlideUtil instance;

    private GlideUtil() {
    }

    public static GlideUtil getInstance() {
        if (instance == null) {
            instance = new GlideUtil();
        }
        return instance;
    }





    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    public static void displayEspImage(String url, ImageView imageView, int type,int corner) {


//通过Re

        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }



    /**
     * 显示高斯模糊效果（电影详情页）
     */
    private static void displayGaussian(Context context, String url, ImageView imageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    @BindingAdapter("android:displayCircle")
    public static void displayCircle(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .transform(new CircleCrop())
//                .apply(bitmapTransform(new CircleCrop()))
//                .transform(new GlideCircleTransform())
//                .transform(new RoundedCorners(20))
//                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(imageView);

    }

    /**
     * 妹子，电影列表图
     *
     * @param defaultPicType 电影：0；妹子：1； 书籍：2
     */
    @BindingAdapter({"android:displayFadeImage", "android:defaultPicType"})
    public static void displayFadeImage(ImageView imageView, String url, int defaultPicType) {
        displayEspImage(url, imageView, defaultPicType,1);
    }

    /**
     * 电影详情页显示电影图片(等待被替换)（测试的还在，已可以弃用）
     * 没有加载中的图
     */
    @BindingAdapter("android:showImg")
    public static void showImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView);
    }
    @BindingAdapter("android:showRoundImg")
    public static void showRoundImg(ImageView imageView,String url){
        Glide.with(imageView.getContext())
                .load(url)
                .transform(new RoundedCorners( 10))
                .into(imageView);
    }
    @BindingAdapter("android:showTopRoundImg")
    public static void showTopRoundImg(ImageView imageView,String url){
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundCorner(imageView.getContext(),10,10,0,0)))
                .into(imageView);
    }
    /**
     * 电影列表图片
     */
    @BindingAdapter("android:showMovieImg")
    public static void showMovieImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                .into(imageView);
    }

    /**
     * 书籍列表图片
     */
    @BindingAdapter("android:showBookImg")
    public static void showBookImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .override((int) CommonUtils.getDimens(R.dimen.book_detail_width), (int) CommonUtils.getDimens(R.dimen.book_detail_height))
                .into(imageView);
    }

    /**
     * 电影详情页显示高斯背景图
     */
    @BindingAdapter("android:showImgBg")
    public static void showImgBg(ImageView imageView, String url) {
        displayGaussian(imageView.getContext(), url, imageView);
    }


    /**
     * 热门电影头部图片
     */
    @BindingAdapter({"android:displayRandom", "android:imgType"})
    public static void displayRandom(ImageView imageView, int imageUrl, int imgType) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(1500))
                .into(imageView);
    }

    /**
     * 加载固定宽高图片
     */
    @BindingAdapter({"android:imageUrl", "android:imageWidthDp", "android:imageHeightDp"})
    public static void imageUrl(ImageView imageView, String url, int imageWidthDp, int imageHeightDp) {
        Glide.with(imageView.getContext())
                .load(url)
                .override(DensityUtil.dip2px(imageView.getContext(), imageWidthDp), DensityUtil.dip2px(imageView.getContext(), imageHeightDp))
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .centerCrop()
                .into(imageView);
    }
}
