package com.diabin.latte.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * Created by fei on 2017/8/3.
 */

public class BaseDecoration extends DividerItemDecoration {

    private BaseDecoration(int color,int size){
        setDividerLookup(new DividerLookupImpl(color,size));
    }

    public static BaseDecoration create(@ColorInt int color, int size){

        return new BaseDecoration(color, size);
    }

}
