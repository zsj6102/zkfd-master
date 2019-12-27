package com.example.jingbin.zkfudou.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.adapter.TreeAdapter;
import com.example.jingbin.zkfudou.base.BaseFragment;
import com.example.jingbin.zkfudou.bean.wanandroid.TreeBean;
import com.example.jingbin.zkfudou.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.zkfudou.databinding.FragmentTestBinding;
import com.example.jingbin.zkfudou.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.zkfudou.utils.CommonUtils;
import com.example.jingbin.zkfudou.utils.MyImageLoader;
import com.example.jingbin.zkfudou.viewmodel.wan.TreeViewModel;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jingbin
 * @date 2018/09/15.
 * @description 知识体系
 */
public class TreeFragment extends BaseFragment<TreeViewModel, FragmentTestBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private TreeAdapter mTreeAdapter;
    private FragmentActivity activity;
    private boolean isLoadBanner = false;

    @Override
    public int setContent() {
        return R.layout.fragment_test;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static TreeFragment newInstance() {
        return new TreeFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        initRefreshView();
        // 准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    private void initRefreshView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        bindingView.xrvWan.setLayoutManager(layoutManager);
        mTreeAdapter = new TreeAdapter();
        bindingView.xrvWan.setAdapter(mTreeAdapter);
//        HeaderItemTreeBinding oneBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_tree, null, false);
//        bindingView.xrvWan.addHeaderView(oneBinding.getRoot());
//        oneBinding.tvPosition.setOnClickListener(v -> layoutManager.scrollToPositionWithOffset(mTreeAdapter.mProjectPosition + 2, 0));
    }

    @Override
    protected void loadData() {
        getWanAndroidBanner();
        getTree();

    }

    private void getTree() {
        viewModel.getTree().observe(this, new android.arch.lifecycle.Observer<WanAndroidBannerBean>() {
            @Override
            public void onChanged(@Nullable WanAndroidBannerBean treeBean) {
                showContentView();

                if (treeBean != null
                        && treeBean.getResult().getBanners() != null
                        && treeBean.getResult().getBanners().size() > 0) {

                    mTreeAdapter.setNewData(treeBean.getResult().getBanners());
//                    bindingView.xrvWan.loadMoreComplete();

                    mIsFirst = false;
                } else {
                    if (mIsFirst) {
                        showError();
                    } else {
                        bindingView.xrvWan.loadMoreComplete();
                    }
                }
            }
        });
    }

    public void getWanAndroidBanner() {
        viewModel.getWanAndroidBanner().observe(this, new Observer<WanAndroidBannerBean>() {
            @Override
            public void onChanged(@Nullable WanAndroidBannerBean bean) {
                if (bean != null) {
                    showContentView();
                    bindingView.rlBanner.setVisibility(View.VISIBLE);
                    if (bean.getResult() != null && bean.getResult().getBanners() != null && bean.getResult().getBanners().size() > 0) {
                        List<String> stringList = new ArrayList<>();
                        stringList.clear();
                        for (int i = 0; i < bean.getResult().getBanners().size(); i++) {
                            stringList.add(bean.getResult().getBanners().get(i).getAdv_code());
                        }
                        bindingView.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int i, float v, int i1) {

                            }

                            @Override
                            public void onPageSelected(int i) {
                                bindingView.arcView.setmBgColor(bean.getResult().getBanners().get(i).getAdv_bgcolor());
                            }

                            @Override
                            public void onPageScrollStateChanged(int i) {

                            }
                        });
                        showBannerView(stringList);
                    }

                } else {
                    bindingView.rlBanner.setVisibility(View.GONE);
                }
            }
        });
    }

    public void showBannerView(List<String> result) {
        if (!isLoadBanner) {
            bindingView.banner.setImageLoader(new MyImageLoader());
            bindingView.banner.setIndicatorGravity(BannerConfig.CENTER);//指示器位置
            bindingView.banner.setImages(result);
            bindingView.banner.start();

            isLoadBanner = true;
        } else {
            bindingView.banner.update(result);
        }
    }

}
