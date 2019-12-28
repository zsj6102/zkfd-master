package com.example.jingbin.zkfudou.bean.home;

import java.util.List;

public class HomeProductBean {
    private List<ItemProduct> hot_products;
    private List<ItemProduct> recently_products;
    private List<ItemProduct> good_products;

    public List<ItemProduct> getHot_products() {
        return hot_products;
    }

    public void setHot_products(List<ItemProduct> hot_products) {
        this.hot_products = hot_products;
    }

    public List<ItemProduct> getRecently_products() {
        return recently_products;
    }

    public void setRecently_products(List<ItemProduct> recently_products) {
        this.recently_products = recently_products;
    }

    public List<ItemProduct> getGood_products() {
        return good_products;
    }

    public void setGood_products(List<ItemProduct> good_products) {
        this.good_products = good_products;
    }
}
