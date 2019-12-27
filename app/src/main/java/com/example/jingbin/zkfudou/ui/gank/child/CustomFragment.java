package com.example.jingbin.zkfudou.ui.gank.child;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.adapter.GankAndroidAdapter;
import com.example.jingbin.zkfudou.base.BaseFragment;
import com.example.jingbin.zkfudou.bean.GankIoDataBean;
import com.example.jingbin.zkfudou.databinding.FragmentAndroidBinding;
import com.example.jingbin.zkfudou.utils.RefreshHelper;
import com.example.jingbin.zkfudou.utils.SPUtils;
import com.example.jingbin.zkfudou.utils.ToastUtil;
import com.example.jingbin.zkfudou.viewmodel.gank.GankViewModel;

import me.jingbin.library.ByRecyclerView;

import static com.example.jingbin.zkfudou.app.Constants.GANK_CALA;

/**
 * @author jingbin
 * @data 2018-12-22
 */
public class CustomFragment extends BaseFragment<GankViewModel, FragmentAndroidBinding> {

    private String mType = "all";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    private BottomSheet.Builder builder = null;
    private GankAndroidAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initRecyclerView();
        // 准备就绪
        mIsPrepared = true;
    }

    @Override
    public int setContent() {
        return R.layout.fragment_android;
    }

    @Override
    protected void loadData() {
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }
        loadCustomData();
    }

    private void initData() {
        String type = SPUtils.getString(GANK_CALA, "全部");
        if ("全部".equals(type)) {
            mType = "all";
        } else if ("IOS".equals(type)) {
            mType = "iOS";
        } else {
            mType = type;
        }
        viewModel.setType(mType);
    }

    private void initRecyclerView() {
        // 去掉显示动画
        bindingView.xrvAndroid.setItemAnimator(null);
        adapter = new GankAndroidAdapter();
        View mHeaderView = View.inflate(getContext(), R.layout.header_item_gank_custom, null);
        bindingView.xrvAndroid.addHeaderView(mHeaderView);
        initHeader(mHeaderView);
        RefreshHelper.initLinear(bindingView.xrvAndroid, false);
        bindingView.xrvAndroid.setAdapter(adapter);
        bindingView.xrvAndroid.setOnLoadMoreListener(new ByRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                int page = viewModel.getPage();
                page++;
                viewModel.setPage(page);
                loadCustomData();
            }
        }, 300);
    }

    private void loadCustomData() {
        viewModel.loadGankData().observe(this, new Observer<GankIoDataBean>() {
            @Override
            public void onChanged(@Nullable GankIoDataBean bean) {
                if (bean != null && bean.getResults() != null && bean.getResults().size() > 0) {
                    if (viewModel.getPage() == 1) {
                        showContentView();
                        boolean isAll = "全部".equals(SPUtils.getString(GANK_CALA, "全部"));
                        adapter.setAllType(isAll);
                        adapter.setNewData(bean.getResults());
                    } else {
                        adapter.addData(bean.getResults());
                    }

                    bindingView.xrvAndroid.loadMoreComplete();
                    if (mIsFirst) {
                        mIsFirst = false;
                    }
                } else {
                    if (viewModel.getPage() == 1) {
                        showError();
                    } else {
                        bindingView.xrvAndroid.loadMoreEnd();
                    }
                }
            }
        });
    }

    private void initHeader(View mHeaderView) {
        final TextView txName = (TextView) mHeaderView.findViewById(R.id.tx_name);
        String gankCala = SPUtils.getString(GANK_CALA, "全部");
        txName.setText(gankCala);
        try {
            builder = new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                    .title("选择分类")
                    .sheet(R.menu.gank_bottomsheet)
                    .listener((dialog, which) -> {
                        switch (which) {
                            case R.id.gank_all:
                                if (isOtherType("全部")) {
                                    changeContent(txName, "全部");
                                }
                                break;
                            case R.id.gank_ios:
                                if (isOtherType("IOS")) {
                                    changeContent(txName, "IOS");
                                }
                                break;
                            case R.id.gank_qian:
                                if (isOtherType("前端")) {
                                    changeContent(txName, "前端");
                                }
                                break;
                            case R.id.gank_app:
                                if (isOtherType("App")) {
                                    changeContent(txName, "App");
                                }
                                break;
                            case R.id.gank_movie:
                                if (isOtherType("休息视频")) {
                                    changeContent(txName, "休息视频");
                                }
                                break;
                            case R.id.gank_resouce:
                                if (isOtherType("拓展资源")) {
                                    changeContent(txName, "拓展资源");
                                }
                                break;
                            default:
                                break;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        View view = mHeaderView.findViewById(R.id.ll_choose_catalogue);
        view.setOnClickListener(v -> {
            if (builder != null) {
                builder.show();
            }
        });
    }

    private void changeContent(TextView textView, String content) {
        if ("全部".equals(content)) {
            textView.setText("全部");
            // 全部传 all
            mType = "all";

        } else if ("IOS".equals(content)) {
            textView.setText("IOS");
            // 这里有严格大小写
            mType = "iOS";

        } else {
            textView.setText(content);
            mType = content;
        }
        viewModel.setType(mType);
        viewModel.setPage(1);
        SPUtils.putString(GANK_CALA, content);
        showLoading();
        loadCustomData();
    }

    private boolean isOtherType(String selectType) {
        String clickText = SPUtils.getString(GANK_CALA, "全部");
        if (clickText.equals(selectType)) {
            ToastUtil.showToast("当前已经是" + selectType + "分类");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 加载失败后点击后的操作
     */
    @Override
    protected void onRefresh() {
        loadCustomData();
    }
}
