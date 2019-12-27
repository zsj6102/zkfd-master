package com.example.jingbin.zkfudou.viewmodel.wan;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.jingbin.zkfudou.base.BaseListViewModel;
import com.example.jingbin.zkfudou.bean.CollectUrlBean;
import com.example.jingbin.zkfudou.http.HttpClient;

import java.util.Collections;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author jingbin
 * @data 2018/5/9
 * @Description 文章列表ViewModel
 */

public class CollectLinkModel extends BaseListViewModel {

    public CollectLinkModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<CollectUrlBean> getCollectUrlList() {
        final MutableLiveData<CollectUrlBean> data = new MutableLiveData<>();
        Disposable subscribe = HttpClient.Builder.getWanAndroidServer().getCollectUrlList()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CollectUrlBean>() {
                    @Override
                    public void accept(CollectUrlBean collectUrlBean) throws Exception {
                        if (collectUrlBean != null && collectUrlBean.getData() != null && collectUrlBean.getData().size() > 0) {
                            // 对集合中的数据进行倒叙排序
                            Collections.reverse(collectUrlBean.getData());
                            data.setValue(collectUrlBean);
                        } else {
                            data.setValue(null);
                        }
                    }
                }, throwable -> data.setValue(null));
        addDisposable(subscribe);
        return data;
    }
}
