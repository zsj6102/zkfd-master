package com.example.jingbin.zkfudou.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.BaseActivity;
import com.example.jingbin.zkfudou.databinding.ActivityNavDownloadBinding;
import com.example.jingbin.zkfudou.utils.PerfectClickListener;
import com.example.jingbin.zkfudou.utils.QRCodeUtil;
import com.example.jingbin.zkfudou.utils.ShareUtils;
import com.example.jingbin.zkfudou.viewmodel.menu.NoViewModel;

public class NavDownloadActivity extends BaseActivity<NoViewModel, ActivityNavDownloadBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_download);
        showContentView();

        setTitle("扫码下载");
        String url = "https://fir.im/cloudreader";
        QRCodeUtil.showThreadImage(this, url, bindingView.ivErweima, R.drawable.ic_cloudreader_mip);
        bindingView.tvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDownloadActivity.class);
        mContext.startActivity(intent);
    }
}
