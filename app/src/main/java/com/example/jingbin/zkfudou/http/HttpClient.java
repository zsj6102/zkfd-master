package com.example.jingbin.zkfudou.http;

import com.example.http.HttpUtils;
import com.example.http.utils.BuildFactory;
import com.example.jingbin.zkfudou.bean.ZkBaseResultBean;

import com.example.jingbin.zkfudou.bean.home.BannerBean;
import com.example.jingbin.zkfudou.bean.home.HomeProductBean;
import com.example.jingbin.zkfudou.bean.home.WanAndroidBannerBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 * @author jingbin
 * @date 16/11/21
 * 网络请求类（一个接口一个方法）
 */
public interface HttpClient {

    class Builder {

        public static HttpClient getFirServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_FIR);
        }

        public static HttpClient getWanAndroidServer() {
            return BuildFactory.getInstance().create(HttpClient.class, HttpUtils.API_WAN_ANDROID);
        }
        public static HttpClient getZkServer(){
            return BuildFactory.getInstance().create(HttpClient.class,HttpUtils.API_ZK);
        }
    }

    /**
     * 中康首页轮播图
     */
    @GET("Commoninfo/getIndexAdList")
    Observable<WanAndroidBannerBean> getWanAndroidBanner(@Query("type") int type);
    /**
     * 中康首页轮播图
     */
    @GET("Commoninfo/getIndexAdList")
    Observable<ZkBaseResultBean<BannerBean>> getZkHotBanner(@Query("type") int type);


    @GET("Index/getProductList")
    Observable<ZkBaseResultBean<HomeProductBean>> getZkLikeList();













}