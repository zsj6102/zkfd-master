package com.example.jingbin.zkfudou.adapter;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.bean.wanandroid.WxarticleItemBean;
import com.example.jingbin.zkfudou.databinding.ItemWxarticleBinding;
import com.example.jingbin.zkfudou.utils.CommonUtils;

/**
 * Created by jingbin on 2019/9/29.
 */

public class WxArticleAdapter extends BaseBindingAdapter<WxarticleItemBean, ItemWxarticleBinding> {

    private int id;
    private int selectPosition = 0;
    private int lastPosition = 0;

    public WxArticleAdapter() {
        super(R.layout.item_wxarticle);
    }

    @Override
    protected void bindView(WxarticleItemBean dataBean, ItemWxarticleBinding binding, int position) {
        if (dataBean != null) {

            if (dataBean.getId() == id) {
                binding.tvTitle.setTextColor(CommonUtils.getColor(R.color.colorTheme));
                binding.viewLine.setBackgroundColor(CommonUtils.getColor(R.color.colorTheme));
            } else {
                binding.tvTitle.setTextColor(CommonUtils.getColor(R.color.select_navi_text));
                binding.viewLine.setBackgroundColor(CommonUtils.getColor(R.color.colorSubtitle));
            }
            binding.setBean(dataBean);
            binding.clWxarticle.setOnClickListener(v -> {
                if (selectPosition != position) {
                    lastPosition = selectPosition;

                    id = dataBean.getId();
                    selectPosition = position;

                    notifyItemChanged(lastPosition);
                    notifyItemChanged(selectPosition);

                    if (listener != null) {
                        listener.onSelected(position);
                    }
                }
            });
        }
    }

    private OnSelectListener listener;

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSelectListener {
        void onSelected(int position);
    }

    public void setId(int id) {
        this.id = id;
    }
}
