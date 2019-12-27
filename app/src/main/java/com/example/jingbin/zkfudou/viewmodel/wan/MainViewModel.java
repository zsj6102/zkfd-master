package com.example.jingbin.zkfudou.viewmodel.wan;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.example.jingbin.zkfudou.base.BaseViewModel;
import com.example.jingbin.zkfudou.bean.wanandroid.BaseResultBean;
import com.example.jingbin.zkfudou.bean.wanandroid.CoinUserInfoBean;
import com.example.jingbin.zkfudou.data.UserUtil;
import com.example.jingbin.zkfudou.data.impl.OnUserInfoListener;
import com.example.jingbin.zkfudou.data.room.User;
import com.example.jingbin.zkfudou.http.HttpClient;
import com.example.jingbin.zkfudou.utils.DataUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
    private final MutableLiveData<CoinUserInfoBean> coin = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        isShowAdmire.set(DataUtil.isShowAdmire());
    }

    public void getUserInfo() {
        UserUtil.getUserInfo(new OnUserInfoListener() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    execute(HttpClient.Builder.getWanAndroidServer().getCoinUserInfo(),
                            new Observer<BaseResultBean<CoinUserInfoBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(BaseResultBean<CoinUserInfoBean> bean) {
                            if (bean != null && bean.getData() != null) {
                                CoinUserInfoBean infoBean = bean.getData();
                                infoBean.setUsername(user.getUsername());
                                coin.setValue(infoBean);

                                UserUtil.getUserInfo(new OnUserInfoListener() {
                                    @Override
                                    public void onSuccess(User user) {
                                        if (user != null) {
                                            user.setCoinCount(infoBean.getCoinCount());
                                            user.setRank(infoBean.getRank());
                                            UserUtil.setUserInfo(user);
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            coin.setValue(null);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                } else {
                    coin.setValue(null);
                }
            }
        });
    }

    public MutableLiveData<CoinUserInfoBean> getCoin() {
        return coin;
    }
}
