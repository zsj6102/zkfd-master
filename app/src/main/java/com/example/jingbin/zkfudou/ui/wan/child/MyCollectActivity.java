package com.example.jingbin.zkfudou.ui.wan.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.BaseActivity;
import com.example.jingbin.zkfudou.databinding.ActivityMyCollectBinding;
import com.example.jingbin.zkfudou.view.MyFragmentPagerAdapter;
import com.example.jingbin.zkfudou.viewmodel.menu.NoViewModel;

import java.util.ArrayList;

/**
 * 玩安卓收藏
 *
 * @author jingbin
 */
public class MyCollectActivity extends BaseActivity<NoViewModel, ActivityMyCollectBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(3);
    private ArrayList<Fragment> mFragments = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        showLoading();
        setTitle("我的收藏");
        initFragmentList();
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        bindingView.vpMyCollect.setAdapter(myAdapter);
        bindingView.vpMyCollect.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        bindingView.tabMyCollect.setupWithViewPager(bindingView.vpMyCollect);
        showContentView();
    }

    private void initFragmentList() {
        mTitleList.clear();
        mTitleList.add("文章");
        mTitleList.add("网址");
        mTitleList.add("段子");
        mFragments.add(CollectArticleFragment.newInstance());
        mFragments.add(CollectLinkFragment.newInstance());
        mFragments.add(JokeFragment.newInstance("段子"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTitleList.clear();
        mFragments.clear();
        mTitleList = null;
        mFragments = null;
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, MyCollectActivity.class);
        mContext.startActivity(intent);
    }
}