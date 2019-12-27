package com.example.jingbin.zkfudou.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.adapter.CollectUrlAdapter;
import com.example.jingbin.zkfudou.base.BaseFragment;
import com.example.jingbin.zkfudou.bean.CollectUrlBean;
import com.example.jingbin.zkfudou.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.zkfudou.utils.CommonUtils;
import com.example.jingbin.zkfudou.utils.RefreshHelper;
import com.example.jingbin.zkfudou.viewmodel.wan.CollectLinkModel;

/**
 * @author jingbin
 * @date 2018/09/27.
 * @description 网址
 */
public class CollectLinkFragment extends BaseFragment<CollectLinkModel, FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FragmentActivity activity;
    private CollectUrlAdapter mAdapter;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static CollectLinkFragment newInstance() {
        return new CollectLinkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();

        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initRefreshView() {
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        RefreshHelper.initLinear(bindingView.xrvWan, true);
        RefreshHelper.setDefaultAnimator(bindingView.xrvWan).setLoadMoreEnabled(true);
        mAdapter = new CollectUrlAdapter(activity);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.srlWan.setOnRefreshListener(() -> bindingView.srlWan.postDelayed(this::getCollectUrlList, 300));
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getCollectUrlList, 150);
    }

    private void getCollectUrlList() {
        viewModel.getCollectUrlList().observe(this, new Observer<CollectUrlBean>() {
            @Override
            public void onChanged(@Nullable CollectUrlBean bean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (bean != null) {
                    if (bean.getData() != null && bean.getData() != null && bean.getData().size() > 0) {
                        showContentView();
                        mAdapter.clear();
                        mAdapter.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                        bindingView.xrvWan.loadMoreEnd();
                    } else {
                        showEmptyView("你还没有收藏网址哦~");
                    }
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    showError();
                }
            }
        });
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getCollectUrlList();
    }
}