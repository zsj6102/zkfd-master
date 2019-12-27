package com.example.jingbin.zkfudou.view;

import android.view.View;

import com.example.jingbin.zkfudou.R;

/**
 * @author jingbin
 */
public class StickyViewImpl implements StickyView {

    @Override
    public boolean isStickyView(View view) {
        return view.getId() == R.id.id_by_sticky_item;
    }

    @Override
    public int getStickViewType() {
        return StickyView.TYPE_STICKY_VIEW;
    }
}
