package com.example.jingbin.zkfudou.ui.wan.child;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.adapter.CoinAdapter;
import com.example.jingbin.zkfudou.base.BaseFragment;
import com.example.jingbin.zkfudou.bean.CoinBean;
import com.example.jingbin.zkfudou.data.UserUtil;
import com.example.jingbin.zkfudou.data.impl.OnUserInfoListener;
import com.example.jingbin.zkfudou.data.room.User;
import com.example.jingbin.zkfudou.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.zkfudou.databinding.HeaderCoinDetailBinding;
import com.example.jingbin.zkfudou.utils.CommonUtils;
import com.example.jingbin.zkfudou.utils.RefreshHelper;
import com.example.jingbin.zkfudou.viewmodel.wan.CoinListViewModel;

import me.jingbin.library.ByRecyclerView;

/**
 * @author jingbin
 * @date 2019/09/26.
 * @description 积分详情
 */
public class CoinDetailFragment extends BaseFragment<CoinListViewModel, FragmentWanAndroidBinding> {

    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FragmentActivity activity;
    private CoinAdapter mAdapter;
    private HeaderCoinDetailBinding headerBinding;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static CoinDetailFragment newInstance() {
        return new CoinDetailFragment();
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
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_coin_detail, (ViewGroup) bindingView.xrvWan.getParent(), false);
        RefreshHelper.initLinear(bindingView.xrvWan, true, 1);
        bindingView.srlWan.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        mAdapter = new CoinAdapter(activity, false);
        bindingView.xrvWan.setAdapter(mAdapter);
        bindingView.xrvWan.addHeaderView(headerBinding.getRoot());
        headerBinding.tvHeaderCoin.setVisibility(View.INVISIBLE);
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.xrvWan.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.setPage(1);
                        getCoinLog();
                    }
                }, 150);
            }
        });
        bindingView.xrvWan.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!bindingView.srlWan.isRefreshing()) {
                    int page = viewModel.getPage();
                    viewModel.setPage(++page);
                    getCoinLog();
                } else {
                    bindingView.xrvWan.loadMoreComplete();
                }
            }
        });
        UserUtil.getUserInfo(new OnUserInfoListener() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    headerBinding.tvHeaderCoin.setText(String.valueOf(user.getCoinCount()));
                }
            }
        });
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getCoinLog, 150);
    }

    private void getCoinLog() {
        viewModel.getCoinLog().observe(this, new Observer<CoinBean>() {
            @Override
            public void onChanged(@Nullable CoinBean homeListBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (homeListBean != null) {
                    if (homeListBean.getDatas() != null && homeListBean.getDatas().size() > 0) {
                        if (viewModel.getPage() == 1) {
                            showContentView();
                            headerBinding.tvHeaderCoin.setVisibility(View.VISIBLE);
                            mAdapter.clear();
                            mAdapter.notifyDataSetChanged();
                        }
                        int positionStart = mAdapter.getItemCount() + 1;
                        mAdapter.addAll(homeListBean.getDatas());
                        mAdapter.notifyItemRangeInserted(positionStart, homeListBean.getDatas().size());
                        bindingView.xrvWan.loadMoreComplete();
                    } else {
                        if (viewModel.getPage() == 1) {
                            showEmptyView("还没有积分信息哦~");
                        } else {
                            bindingView.xrvWan.loadMoreEnd();
                        }
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
        getCoinLog();
    }
}
