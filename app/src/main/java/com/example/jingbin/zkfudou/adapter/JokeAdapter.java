package com.example.jingbin.zkfudou.adapter;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.bean.wanandroid.DuanZiBean;
import com.example.jingbin.zkfudou.databinding.ItemJokeBinding;
import com.example.jingbin.zkfudou.utils.TimeUtil;

/**
 * Created by jingbin on 2016/11/25.
 */

public class JokeAdapter extends BaseBindingAdapter<DuanZiBean, ItemJokeBinding> {

    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void bindView(DuanZiBean bean, ItemJokeBinding binding, int position) {
        binding.setBean(bean);
        String time = TimeUtil.formatDataTime(Long.valueOf(bean.getCreateTime() + "000"));
        binding.setTime(time);
    }
}
