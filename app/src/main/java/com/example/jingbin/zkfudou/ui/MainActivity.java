package com.example.jingbin.zkfudou.ui;

import android.content.Context;

import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import com.example.jingbin.zkfudou.R;

import com.example.jingbin.zkfudou.base.BaseActivity;

import com.example.jingbin.zkfudou.databinding.ActivityMainBinding;

import com.example.jingbin.zkfudou.ui.home.HomeFragment;
import com.example.jingbin.zkfudou.utils.StatusBarUtil;
import com.example.jingbin.zkfudou.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

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
    }



    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    private void initView() {

    }

    /**
     * inflateHeaderView 进来的布局要宽一些
     */

    private void initContentFragment() {

        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(HomeFragment.newInstance());
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
