package com.example.jingbin.zkfudou.viewmodel;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author jingbin
 * @data 2018/2/8
 * @Description
 */

public interface WanNavigator {


    interface JokeModelNavigator {


        void loadFailed();

        void addSubscription(Disposable disposable);

    }

    /**
     * 收藏或取消收藏
     */
    interface OnCollectNavigator {
        void onSuccess();

        void onFailure();
    }

}
