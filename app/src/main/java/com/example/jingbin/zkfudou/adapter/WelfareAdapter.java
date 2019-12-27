package com.example.jingbin.zkfudou.adapter;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.bean.GankIoDataBean;
import com.example.jingbin.zkfudou.databinding.ItemWelfareBinding;

/**
 * @author jingbin
 * @date 2019/11/7
 */
public class WelfareAdapter extends BaseBindingAdapter<GankIoDataBean.ResultBean, ItemWelfareBinding> {

    public WelfareAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void bindView(GankIoDataBean.ResultBean bean, ItemWelfareBinding binding, int position) {
        binding.setBean(bean);
    }

}
