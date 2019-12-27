package com.example.jingbin.zkfudou.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.adapter.HomeHotAdapter;
import com.example.jingbin.zkfudou.base.BaseFragment;
import com.example.jingbin.zkfudou.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.zkfudou.databinding.FragmentZkHomeBinding;

import com.example.jingbin.zkfudou.utils.MyImageLoader;
import com.example.jingbin.zkfudou.viewmodel.wan.WanAndroidListViewModel;
import com.youth.banner.BannerConfig;
import java.util.ArrayList;
import java.util.List;


/**
 * 中康首页
 *
 * @author jingbin
 * Updated on 18/02/07...19/05/16
 */
public class HomeFragment extends BaseFragment<WanAndroidListViewModel, FragmentZkHomeBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private HomeHotAdapter mAdapter;

    private boolean isLoadBanner = false;
    // banner图的宽
    private int width;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public int setContent() {
        return R.layout.fragment_zk_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRefreshView();
        // 准备就绪
        mIsPrepared = true;
        loadData();
    }

    private void initRefreshView() {

        mAdapter = new HomeHotAdapter(getActivity());
        bindingView.xrvWan.setAdapter(mAdapter);
    }


    /**
     * 设置banner图
     */
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


    @Override
    protected void loadData() {
        if (mIsPrepared && isLoadBanner) {
            onResume();
        }
        getWanAndroidBanner();
        getHot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoadBanner) {
            bindingView.banner.startAutoPlay();
        }
    }

    @Override
    protected void onInvisible() {
        if (mIsPrepared && isLoadBanner) {
            onPause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 不可见时轮播图停止滚动
        if (isLoadBanner) {
            bindingView.banner.stopAutoPlay();
        }
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
    private void getHot(){
        viewModel.getHomeHotList().observe(this, new Observer<WanAndroidBannerBean>() {
            @Override
            public void onChanged(@Nullable WanAndroidBannerBean wanAndroidBannerBean) {
                showContentView();
                mAdapter.setNewData(wanAndroidBannerBean.getResult().getBanners());
            }
        });
    }
//    private void getHomeArticleList() {
//        viewModel.getHomeArticleList(null).observe(this, observer);
//    }
//
//    private void getHomeProjectList() {
//        viewModel.getHomeProjectList().observe(this, observer);
//    }

//    private Observer<HomeListBean> observer = new Observer<HomeListBean>() {
//        @Override
//        public void onChanged(@Nullable HomeListBean homeListBean) {
//            if (bindingView.srlWan.isRefreshing()) {
//                bindingView.srlWan.setRefreshing(false);
//            }
//
//            if (homeListBean != null
//                    && homeListBean.getData() != null
//                    && homeListBean.getData().getDatas() != null
//                    && homeListBean.getData().getDatas().size() > 0) {
//                if (viewModel.getPage() == 0) {
//                    showContentView();
//                    mAdapter.setNewData(homeListBean.getData().getDatas());
//                } else {
//                    mAdapter.addData(homeListBean.getData().getDatas());
//                    bindingView.xrvWan.loadMoreComplete();
//                }
//
//                if (mIsFirst && viewModel.getPage() == 0) {
//                    if (isLoadBanner) {
//                        headerBinding.banner.startAutoPlay();
//                    }
//                    mIsFirst = false;
//                }
//            } else {
//                if (viewModel.getPage() == 0) {
//                    showError();
//                } else {
//                    bindingView.xrvWan.loadMoreEnd();
//                }
//            }
//        }
//    };
//

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isLoadBanner) {
            bindingView.banner.stopAutoPlay();
            bindingView.banner.releaseBanner();
            isLoadBanner = false;
        }
        if (mAdapter != null) {
            mAdapter.clear();
            mAdapter = null;
        }
    }
}
