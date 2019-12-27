package com.example.jingbin.zkfudou.ui.menu;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.BaseActivity;
import com.example.jingbin.zkfudou.databinding.ActivityNavAdmireBinding;
import com.example.jingbin.zkfudou.utils.CommonUtils;
import com.example.jingbin.zkfudou.utils.DialogBuild;
import com.example.jingbin.zkfudou.utils.GlideUtil;
import com.example.jingbin.zkfudou.utils.PermissionHandler;
import com.example.jingbin.zkfudou.utils.RxSaveImage;
import com.example.jingbin.zkfudou.view.webview.WebViewActivity;
import com.example.jingbin.zkfudou.viewmodel.menu.NoViewModel;

/**
 * @author jingbin
 */
public class NavAdmireActivity extends BaseActivity<NoViewModel, ActivityNavAdmireBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_admire);
        showContentView();
        setTitle("赞赏云阅");

        bindingView.tvFunction.setOnClickListener(v -> WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_update_log), "更新日志"));
        bindingView.tvAdmire.setOnClickListener(v -> WebViewActivity.loadUrl(v.getContext(), CommonUtils.getString(R.string.string_url_admire), "赞赏记录"));

        String wechat = "https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/Wechat-admire.jpg";
        String alipay = "https://raw.githubusercontent.com/youlookwhat/CloudReader/master/file/alipay-admire.jpg";
        GlideUtil.imageUrl(bindingView.ivWechat, wechat, 200, 200);
        GlideUtil.imageUrl(bindingView.ivAlipay, alipay, 200, 200);
        bindingView.ivWechat.setOnClickListener(v -> saveImage(v, wechat));
        bindingView.ivAlipay.setOnClickListener(v -> saveImage(v, alipay));
    }

    private void saveImage(View v, String url) {
        DialogBuild.showCustom(v, "是否保存图片到本地?", "保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!PermissionHandler.isHandlePermission(NavAdmireActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    return;
                }
                RxSaveImage.saveImageToGallery(NavAdmireActivity.this, url, url);
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavAdmireActivity.class);
        mContext.startActivity(intent);
    }

}
