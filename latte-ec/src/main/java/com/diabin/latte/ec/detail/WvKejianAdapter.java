package com.diabin.latte.ec.detail;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.diabin.latte.ec.R;
import com.diabin.latte.ui.recycler.ItemType;
import com.diabin.latte.ui.recycler.MultIpleViewHolder;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.ui.recycler.MultipleRecyclerAdapter;

import java.util.List;

//import com.diabin.latte.ui.recycler.MultipleViewHolder;

/**
 * Created by 傅令杰
 */

public class WvKejianAdapter extends MultipleRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .dontAnimate();

    protected WvKejianAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image);
    }

    @Override
    protected void convert(MultIpleViewHolder holder, MultipleItemEntity entity) {
        super.convert(holder, entity);
        final int type = holder.getItemViewType();
        switch (type) {
            case ItemType.SINGLE_BIG_IMAGE:
                final WebView imageView = holder.getView(R.id.wv_qian_detail);
                final WebView webView = holder.getView(R.id.wv_qian_detail);
                final String url = entity.getField(MultipleFields.IMAGE_URL);

                //imageView.setScrollBarStyle(0);
                WebSettings webSettings = imageView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setAllowFileAccess(true);
                webSettings.setBuiltInZoomControls(true);
                imageView.loadUrl(url);
                imageView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });


            /*    Glide.with(mContext)
                        .load(url)

                        .into(imageView);*/
                break;
            default:
                break;
        }
    }
}
