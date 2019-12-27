package com.example.jingbin.zkfudou.viewmodel.wan;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.zkfudou.base.BaseViewModel;
import com.example.jingbin.zkfudou.bean.wanandroid.TreeBean;
import com.example.jingbin.zkfudou.bean.wanandroid.WanAndroidBannerBean;
import com.example.jingbin.zkfudou.http.HttpClient;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2018/12/3
 * @Description wanandroid知识体系的ViewModel
 */

public class TreeViewModel extends BaseViewModel {


    public TreeViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<WanAndroidBannerBean> getWanAndroidBanner() {
        final MutableLiveData<com.example.jingbin.zkfudou.bean.wanandroid.WanAndroidBannerBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getZkServer().getWanAndroidBanner(23)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<com.example.jingbin.zkfudou.bean.wanandroid.WanAndroidBannerBean>() {

                    @Override
                    public void accept(com.example.jingbin.zkfudou.bean.wanandroid.WanAndroidBannerBean bannerBean) throws Exception {
                        if (bannerBean != null
                                && bannerBean.getResult() != null
                                && bannerBean.getResult().getBanners().size() > 0) {
                            data.setValue(bannerBean);
                        } else {
                            data.setValue(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        data.setValue(null);
                    }
                });
        addDisposable(subscribe);
        return data;
    }

    public MutableLiveData<WanAndroidBannerBean> getTree() {
        final MutableLiveData<WanAndroidBannerBean> hotList = new MutableLiveData<>();
        HttpClient.Builder.getZkServer().getZkHotBanner(24)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WanAndroidBannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(WanAndroidBannerBean wanAndroidBannerBean) {
                        hotList.setValue(wanAndroidBannerBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        hotList.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return hotList;
    }
}
