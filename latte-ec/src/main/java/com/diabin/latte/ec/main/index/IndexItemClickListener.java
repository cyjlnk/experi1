package com.diabin.latte.ec.main.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.detail.GoodsDetailDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fei on 2017/8/3.
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    public IndexItemClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static SimpleClickListener create(LatteDelegate delegate){
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
     /*   final Intent intent =new Intent(DELEGATE.,GoodsDetailDelegate.class);
        final ArrayList<String> list1 =new ArrayList<>();
        list1.addAll(adapter.getData());
        intent.putStringArrayListExtra("",list1);
        intent .*/
        final GoodsDetailDelegate delegate =GoodsDetailDelegate.create();
        DELEGATE.start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
