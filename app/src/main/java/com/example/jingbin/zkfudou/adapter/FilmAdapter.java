package com.example.jingbin.zkfudou.adapter;

import android.app.Activity;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.bean.moviechild.FilmItemBean;
import com.example.jingbin.zkfudou.databinding.ItemFilmBinding;
import com.example.jingbin.zkfudou.ui.film.child.FilmDetailActivity;
import com.example.jingbin.zkfudou.utils.PerfectClickListener;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * @author jingbin
 */
public class FilmAdapter extends BaseBindingAdapter<FilmItemBean, ItemFilmBinding> {

    private Activity activity;

    public FilmAdapter(Activity activity) {
        super(R.layout.item_film);
        this.activity = activity;
    }

    @Override
    protected void bindView(FilmItemBean positionData, ItemFilmBinding binding, int position) {
        if (positionData != null) {
            binding.setSubjectsBean(positionData);
            binding.llOneItem.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    FilmDetailActivity.start(activity, positionData, binding.ivOnePhoto);
                }
            });

            ViewHelper.setScaleX(binding.getRoot(), 0.8f);
            ViewHelper.setScaleY(binding.getRoot(), 0.8f);
            ViewPropertyAnimator.animate(binding.getRoot()).scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
            ViewPropertyAnimator.animate(binding.getRoot()).scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        }
    }
}
