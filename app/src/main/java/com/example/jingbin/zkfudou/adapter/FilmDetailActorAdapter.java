package com.example.jingbin.zkfudou.adapter;

import com.example.jingbin.zkfudou.R;
import com.example.jingbin.zkfudou.base.binding.BaseBindingAdapter;
import com.example.jingbin.zkfudou.bean.FilmDetailBean;
import com.example.jingbin.zkfudou.databinding.ItemFilmDetailActorBinding;

/**
 * Created by jingbin on 2016/12/10.
 */

public class FilmDetailActorAdapter extends BaseBindingAdapter<FilmDetailBean.ActorsBean, ItemFilmDetailActorBinding> {

    public FilmDetailActorAdapter() {
        super(R.layout.item_film_detail_actor);
    }

    @Override
    protected void bindView(FilmDetailBean.ActorsBean bean, ItemFilmDetailActorBinding binding, int position) {
        binding.setPersonBean(bean);
    }
}
