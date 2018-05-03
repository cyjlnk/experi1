package com.diabin.latte.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by fei on 2017/8/3.
 */

public class MultIpleViewHolder extends BaseViewHolder {

    public MultIpleViewHolder(View view) {
        super(view);
    }

    public static MultIpleViewHolder create(View view){
        return new MultIpleViewHolder(view);
    }
}
