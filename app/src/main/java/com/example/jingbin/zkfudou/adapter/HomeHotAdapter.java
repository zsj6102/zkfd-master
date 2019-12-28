package com.example.jingbin.zkfudou.adapter;

import android.app.Activity;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.bean.home.DataBean;
import com.example.jingbin.zkfudou.databinding.ItemLayoutHotBinding;

public class HomeHotAdapter extends BaseBindingAdapter<DataBean, ItemLayoutHotBinding> {
    private Activity activity;

    public HomeHotAdapter(Activity activity) {
        super(R.layout.item_layout_hot);
        this.activity = activity;

    }

    @Override
    protected void bindView(DataBean bean, ItemLayoutHotBinding binding, int position) {
         if(bean!=null){
             binding.setBean(bean);
             binding.setAdapter(HomeHotAdapter.this);
         }
    }
    public void openDetail(DataBean bean) {

    }
}
