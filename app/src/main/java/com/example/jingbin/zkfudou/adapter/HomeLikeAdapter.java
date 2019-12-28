package com.example.jingbin.zkfudou.adapter;

import android.app.Activity;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.bean.home.DataBean;
import com.example.jingbin.zkfudou.bean.home.ItemProduct;
import com.example.jingbin.zkfudou.databinding.ItemLayoutLikeBinding;

public class HomeLikeAdapter extends BaseBindingAdapter<ItemProduct, ItemLayoutLikeBinding> {
    private Activity activity;

    public HomeLikeAdapter(Activity activity) {
        super(R.layout.item_layout_like);
        this.activity = activity;

    }

    @Override
    protected void bindView(ItemProduct bean, ItemLayoutLikeBinding binding, int position) {
        if(bean!=null){
            binding.setBean(bean);
            binding.setAdapter(HomeLikeAdapter.this);
        }
    }
    public void openDetail(DataBean bean) {

    }
}
