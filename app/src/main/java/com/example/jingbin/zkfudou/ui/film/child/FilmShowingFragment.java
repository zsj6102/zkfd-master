package com.example.jingbin.zkfudou.ui.film.child;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.adapter.FilmAdapter;
import com.example.jingbin.zkfudou.base.BaseFragment;
import com.example.jingbin.zkfudou.bean.MtimeFilmeBean;
import com.example.jingbin.zkfudou.databinding.FragmentWanAndroidBinding;
import com.example.jingbin.zkfudou.utils.CommonUtils;
import com.example.jingbin.zkfudou.utils.DebugUtil;
import com.example.jingbin.zkfudou.viewmodel.movie.FilmViewModel;

/**
 * @author jingbin
 * Updated by jingbin on 19/05/14.
 */
public class FilmShowingFragment extends BaseFragment<FilmViewModel, FragmentWanAndroidBinding> {

    private static final String TYPE = "param1";
    private String mType = "综合";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private FilmAdapter adapter;
    private FragmentActivity activity;

    @Override
    public int setContent() {
        return R.layout.fragment_wan_android;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public static FilmShowingFragment newInstance(String param1) {
        FilmShowingFragment fragment = new FilmShowingFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
        }
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
        adapter = new FilmAdapter(activity);
        bindingView.xrvWan.setLayoutManager(new LinearLayoutManager(activity));
        bindingView.xrvWan.setItemAnimator(null);
        bindingView.xrvWan.setHasFixedSize(true);
        bindingView.xrvWan.setLoadMoreEnabled(true);
        viewModel.bookType.set(mType);
        bindingView.xrvWan.setAdapter(adapter);
        bindingView.srlWan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bindingView.srlWan.postDelayed(() -> {
                    getHotFilm();
                }, 150);
            }
        });
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlWan.setRefreshing(true);
        bindingView.srlWan.postDelayed(this::getHotFilm, 150);
        DebugUtil.error("-----setRefreshing");
    }

    private void getHotFilm() {
        viewModel.getHotFilm().observe(this, new android.arch.lifecycle.Observer<MtimeFilmeBean>() {
            @Override
            public void onChanged(@Nullable MtimeFilmeBean bookBean) {
                if (bindingView.srlWan.isRefreshing()) {
                    bindingView.srlWan.setRefreshing(false);
                }
                if (bookBean != null && bookBean.getMs() != null && bookBean.getMs().size() > 0) {
                    if (viewModel.getStart() == 0) {
                        showContentView();
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                    }
                    int positionStart = adapter.getItemCount();
                    adapter.addAll(bookBean.getMs());
                    adapter.notifyItemRangeInserted(positionStart, bookBean.getMs().size());
                    bindingView.xrvWan.loadMoreEnd();
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    if (adapter.getItemCount() == 0) {
                        showError();
                    } else {
                        bindingView.xrvWan.loadMoreEnd();
                    }
                }
            }
        });
    }

    @Override
    protected void onRefresh() {
        bindingView.srlWan.setRefreshing(true);
        getHotFilm();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.clear();
            adapter = null;
        }
    }
}
