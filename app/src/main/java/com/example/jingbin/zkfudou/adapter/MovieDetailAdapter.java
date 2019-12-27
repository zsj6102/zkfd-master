package com.example.jingbin.zkfudou.adapter;

import android.text.TextUtils;
import android.view.View;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.bean.moviechild.PersonBean;
import com.example.jingbin.zkfudou.databinding.ItemMovieDetailPersonBinding;
import com.example.jingbin.zkfudou.utils.PerfectClickListener;
import com.example.jingbin.zkfudou.view.webview.WebViewActivity;

/**
 * Created by jingbin on 2016/12/10.
 */

public class MovieDetailAdapter extends BaseBindingAdapter<PersonBean, ItemMovieDetailPersonBinding> {

    public MovieDetailAdapter() {
        super(R.layout.item_movie_detail_person);
    }

    @Override
    protected void bindView(PersonBean bean, ItemMovieDetailPersonBinding binding, int position) {
        binding.setPersonBean(bean);
        binding.llItem.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (bean != null && !TextUtils.isEmpty(bean.getAlt())) {
                    WebViewActivity.loadUrl(v.getContext(), bean.getAlt(), bean.getName());
                }
            }
        });
    }
}
