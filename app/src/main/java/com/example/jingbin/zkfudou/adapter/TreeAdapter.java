package com.example.jingbin.zkfudou.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.base.binding.BaseBindingHolder;
import com.example.jingbin.zkfudou.bean.wanandroid.DataBean;
import com.example.jingbin.zkfudou.bean.wanandroid.TreeItemBean;
import com.example.jingbin.zkfudou.databinding.ItemLayoutHotBinding;
import com.example.jingbin.zkfudou.databinding.ItemTreeBinding;
import com.example.jingbin.zkfudou.ui.wan.child.CategoryDetailActivity;
import com.example.jingbin.zkfudou.utils.DataUtil;
import com.example.jingbin.zkfudou.view.ThinBoldSpan;
import com.google.android.flexbox.FlexboxLayout;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by jingbin on 2019/12/04.
 */

public class TreeAdapter extends BaseBindingAdapter<DataBean, ItemLayoutHotBinding> {

//    private LayoutInflater mInflater = null;
//    private Queue<TextView> mFlexItemTextViewCaches = new LinkedList<>();
//
//    public int mProjectPosition = 26;

    public TreeAdapter() {
        super(R.layout.item_layout_hot);
    }

    @Override
    protected void bindView(DataBean dataBean, ItemLayoutHotBinding binding, int position) {
        if (dataBean != null) {
            if(dataBean!=null){
                binding.setBean(dataBean);
                binding.setAdapter(TreeAdapter.this);
            }
//            String name = DataUtil.getHtmlString(dataBean.getName());
//            if ("开源项目主Tab".equals(name)) {
//                mProjectPosition = position;
//            }
//            binding.tvTreeTitle.setText(ThinBoldSpan.getDefaultSpanString(binding.tvTreeTitle.getContext(), name));
//            for (int i = 0; i < dataBean.getChildren().size(); i++) {
//                TreeItemBean.ChildrenBean childItem = dataBean.getChildren().get(i);
//                TextView child = createOrGetCacheFlexItemTextView(binding.flTree);
//                child.setText(DataUtil.getHtmlString(childItem.getName()));
//                child.setOnClickListener(v -> CategoryDetailActivity.start(v.getContext(), childItem.getId(), dataBean));
//                binding.flTree.addView(child);
//            }
        }
    }

//    @Override
//    public void onViewRecycled(@NonNull BaseBindingHolder<TreeItemBean, ItemTreeBinding> holder) {
//        super.onViewRecycled(holder);
//        FlexboxLayout fbl = holder.getView(R.id.fl_tree);
//        for (int i = 0; i < fbl.getChildCount(); i++) {
//            mFlexItemTextViewCaches.offer((TextView) fbl.getChildAt(i));
//        }
//        fbl.removeAllViews();
//    }
//
//    private TextView createOrGetCacheFlexItemTextView(FlexboxLayout fbl) {
//        TextView tv = mFlexItemTextViewCaches.poll();
//        if (tv != null) {
//            return tv;
//        }
//        return createFlexItemTextView(fbl);
//    }
//
//    private TextView createFlexItemTextView(FlexboxLayout fbl) {
//        if (mInflater == null) {
//            mInflater = LayoutInflater.from(fbl.getContext());
//        }
//        return (TextView) mInflater.inflate(R.layout.layout_tree_tag, fbl, false);
//    }
}
