package com.example.jingbin.zkfudou.ui;

import android.content.Context;

import android.content.Intent;
import android.content.res.Configuration;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.jingbin.zkfudou.R;

import com.example.jingbin.zkfudou.base.BaseActivity;

import com.example.jingbin.zkfudou.databinding.ActivityMainBinding;

import com.example.jingbin.zkfudou.http.rx.RxBus;
import com.example.jingbin.zkfudou.http.rx.RxCodeConstants;
import com.example.jingbin.zkfudou.ui.wan.child.HomeFragment;
import com.example.jingbin.zkfudou.ui.wan.child.NavigationFragment;
import com.example.jingbin.zkfudou.ui.wan.child.TreeFragment;
import com.example.jingbin.zkfudou.ui.wan.child.WxArticleFragment;
import com.example.jingbin.zkfudou.utils.CommonUtils;
import com.example.jingbin.zkfudou.utils.UpdateUtil;
import com.example.jingbin.zkfudou.view.statusbar.StatusBarUtil;
import com.example.jingbin.zkfudou.viewmodel.wan.MainViewModel;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


/**
 * Created by zsj on 19/12/25.
 *
 */
public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding>   {

    public static boolean isLaunch;
    private List<Fragment> mFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showContentView();
        isLaunch = true;
        initView();
        initContentFragment();
        initBottomTab();
        initRxBus();
    }

    @Override
    protected void initStatusBar() {
//        StatusBarUtil.setTranslucentForImageView(MainActivity.this,0,null);

    }

    private void initView() {
        setNoTitle();

        UpdateUtil.check(this, false);
    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */

    private void initContentFragment() {

        mFragments = new ArrayList<>();
        mFragments.add(TreeFragment.newInstance());
        mFragments.add(WxArticleFragment.newInstance());
        mFragments.add(TreeFragment.newInstance());
        mFragments.add(NavigationFragment.newInstance());
        mFragments.add(NavigationFragment.newInstance());
        //默认选中第一个
        commitAllowingStateLoss(0);
    }
    private void initBottomTab() {
        NavigationController navigationController = bindingView.include.pagerBottomTab.material()
                .addItem(R.mipmap.yingyong, getResources().getString(R.string.first_page))
                .addItem(R.mipmap.huanzhe,  getResources().getString(R.string.fd_market))
                .addItem(R.mipmap.xiaoxi_select,  getResources().getString(R.string.msg))
                .addItem(R.mipmap.wode_select, getResources().getString(R.string.shop_car))
                .addItem(R.mipmap.huanzhe,getResources().getString(R.string.mine))
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                commitAllowingStateLoss(index);
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }
    private void commitAllowingStateLoss(int position) {
        hideAllFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(position + "");
        if (currentFragment != null) {
            transaction.show(currentFragment);
        } else {
            currentFragment = mFragments.get(position);
            transaction.add(R.id.frameLayout, currentFragment, position + "");
        }
        transaction.commitAllowingStateLoss();
    }

    //隐藏所有Fragment
    private void hideAllFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(i + "");
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
        }
        transaction.commitAllowingStateLoss();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 不退出程序，进入后台
            moveTaskToBack(true);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 每日推荐点击"新电影热映榜"跳转
     */
    private void initRxBus() {
        Disposable subscribe2 = RxBus.getDefault().toObservable(RxCodeConstants.LOGIN, Boolean.class)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isLogin) throws Exception {
                        if (isLogin) {
                            viewModel.getUserInfo();
                        } else {
                            viewModel.getCoin().setValue(null);
                        }
                    }
                });
        addSubscription(subscribe2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isLaunch = false;
        // 杀死该应用进程 需要权限
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
