package com.example.jingbin.zkfudou.base.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.jingbin.library.adapter.BaseByViewHolder;


/**
 * https://github.com/youlookwhat/ByRecyclerView
 */
public abstract class BaseBindingHolder<T, B extends ViewDataBinding> extends BaseByViewHolder<T> {

    public final B binding;

    public BaseBindingHolder(ViewGroup viewGroup, int layoutId) {
        super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), layoutId, viewGroup, false).getRoot());
        binding = DataBindingUtil.getBinding(this.itemView);
    }

    @Override
    protected void onBaseBindView(BaseByViewHolder<T> holder, T bean, int position) {
        onBindingView(this, binding, bean, position);
        binding.executePendingBindings();
    }

    protected abstract void onBindingView(BaseBindingHolder holder, B binding, T bean, int position);
}
