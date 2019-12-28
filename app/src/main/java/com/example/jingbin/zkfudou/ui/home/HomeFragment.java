package com.example.jingbin.zkfudou.ui.home;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.adapter.HomeHotAdapter;
import com.example.jingbin.zkfudou.adapter.HomeLikeAdapter;
import com.example.jingbin.zkfudou.base.BaseFragment;
import com.example.jingbin.zkfudou.bean.ZkBaseResultBean;
import com.example.jingbin.zkfudou.bean.home.BannerBean;
import com.example.jingbin.zkfudou.bean.home.HomeProductBean;
import com.example.jingbin.zkfudou.bean.home.WanAndroidBannerBean;
import com.example.jingbin.zkfudou.databinding.FragmentZkHomeBinding;

import com.example.jingbin.zkfudou.utils.DensityUtil;
import com.example.jingbin.zkfudou.utils.MyImageLoader;
import com.example.jingbin.zkfudou.viewmodel.WanAndroidListViewModel;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import me.jingbin.library.decoration.GridSpaceItemDecoration;
import me.jingbin.library.decoration.SpacesItemDecoration;


/**
 * 中康首页
 *
 * @author jingbin
 * Updated on 18/02/07...19/05/16
 */
public class HomeFragment extends BaseFragment<WanAndroidListViewModel, FragmentZkHomeBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private HomeHotAdapter mTreeAdapter;
    private HomeLikeAdapter homeLikeAdapter;
    private FragmentActivity activity;
    private boolean isLoadBanner = false;

    @Override
    public int setContent() {
        return R.layout.fragment_zk_home;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(activity, SpacesItemDecoration.VERTICAL);
        itemDecoration.setDrawable(R.drawable.shape_line);
        bindingView.xrvWan.addItemDecoration(itemDecoration);
        mTreeAdapter = new HomeHotAdapter(activity);
        homeLikeAdapter = new HomeLikeAdapter(activity);
        bindingView.xrvWan.setAdapter(mTreeAdapter);
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        bindingView.xrvLike.setLayoutManager(staggeredGridLayoutManager);
        GridSpaceItemDecoration itemDecoration1 = new GridSpaceItemDecoration(2, DensityUtil.dip2px(activity, 10));
        itemDecoration1.setNoShowSpace(1, 1);
//        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        bindingView.xrvLike.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGridLayoutManager.invalidateSpanAssignments();
            }
        });
        bindingView.xrvLike.addItemDecoration(itemDecoration1);
        bindingView.xrvLike.setAdapter(homeLikeAdapter);
    }

    @Override
    protected void loadData() {
        getWanAndroidBanner();
        getHot();
        getLike();
    }

    private void getLike() {
        viewModel.getHomeLikeList().observe(this, new Observer<ZkBaseResultBean<HomeProductBean>>() {
            @Override
            public void onChanged(@Nullable ZkBaseResultBean<HomeProductBean> homeProductBeanZkBaseResultBean) {
                showContentView();

                if (homeProductBeanZkBaseResultBean != null
                        && homeProductBeanZkBaseResultBean.getResult().getGood_products() != null
                        && homeProductBeanZkBaseResultBean.getResult().getGood_products().size() > 0) {

                    homeLikeAdapter.setNewData(homeProductBeanZkBaseResultBean.getResult().getGood_products());
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

    private void getHot() {
        viewModel.getHomeHotList().observe(this, new android.arch.lifecycle.Observer<ZkBaseResultBean<BannerBean>>() {
            @Override
            public void onChanged(@Nullable ZkBaseResultBean<BannerBean> treeBean) {
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
