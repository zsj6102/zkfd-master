package com.example.jingbin.zkfudou.viewmodel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.jingbin.zkfudou.base.BaseViewModel;
import com.example.jingbin.zkfudou.utils.DataUtil;

/**
 * @author jingbin
 * @data 2019/9/24
 * @Description 首页ViewModel
 */

public class MainViewModel extends BaseViewModel {

    // 问题反馈是否已读
    public ObservableField<Boolean> isReadOk = new ObservableField<>();
    // 赞赏入口是否开放
    public ObservableField<Boolean> isShowAdmire = new ObservableField<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        isShowAdmire.set(DataUtil.isShowAdmire());
    }


}
