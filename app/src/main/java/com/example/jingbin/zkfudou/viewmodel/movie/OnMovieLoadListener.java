package com.example.jingbin.zkfudou.viewmodel.movie;

import com.example.jingbin.zkfudou.bean.HotMovieBean;

/**
 * @author jingbin
 * @data 2017/12/26
 * @Description
 */

public interface OnMovieLoadListener {

    void onSuccess(HotMovieBean bean);

    void onFailure();
}
