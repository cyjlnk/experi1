package com.diabin.latte.ec.detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.YoYo;
import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.net.RestClient;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.util.log.LatteLogger;
import com.diabin.latte.util.toast.ToastUtil;
import com.diabin.latte_ui.animation.BezierAnimation;
import com.diabin.latte_ui.animation.BezierUtil;
import com.diabin.latte_ui.widget.CircleTextView;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by fei on 2017/8/3.
 */

public class KejianDetailDelegate extends LatteDelegate implements
        AppBarLayout.OnOffsetChangedListener,
        BezierUtil.AnimationListener {
    @BindView(R2.id.wv_qian_detail)
    WebView mWebView = null;
    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    VideoView mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart = null;

    private static final String ARG_GOODS_ID = "ARG_GOODS_ID";
    private int mGoodsId = -1;

    private String mGoodsThumbUrl = null;
    private int mShopCount = 0;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate()
            .override(100, 100);

    @OnClick(R2.id.rl_add_shop_cart)
    void onClickAddShopCart() {
        final CircleImageView animImg = new CircleImageView(getContext());
        Glide.with(this)
                .load(mGoodsThumbUrl)
                .apply(OPTIONS)
                .into(animImg);
        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, animImg, this);
    }

    private void setShopCartCount(JSONObject data) {
        mGoodsThumbUrl = data.getString("thumb");
        if (mShopCount == 0) {
            mCircleTextView.setVisibility(View.GONE);
        }
    }

    public static KejianDetailDelegate create(int goodsId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodsId);
        final KejianDetailDelegate delegate = new KejianDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID);
            ToastUtil.show(getContext(), String.valueOf(mGoodsId));

        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_qianyan_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppBar.addOnOffsetChangedListener(this);
        mCircleTextView.setCircleBackground(Color.RED);
        initData();
        initTabLayout();

    }

    private void initVideo() {

        String path = "http://love.lnkjdx.com/video/lxy/sbq.mp4";
        final MediaController mc = new MediaController(this.getContext());
        mBanner.setMediaController(mc);
        //这里设置网络地址，可以是卫视地址，网络主播地址，总结推送地址
        mBanner.setVideoPath(path);
        //VideoView是否准备好 的回调方法
        mBanner.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                mp.pause();
            }
        });

    }


    private void initPager(JSONObject data) {
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), data);
        mViewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        final Context context = getContext();
        if (context != null) {
            mTabLayout.setSelectedTabIndicatorColor
                    (ContextCompat.getColor(context, R.color.app_main));
        }
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initData() {
        RestClient.builder()
                .url("goods_detail.php")
                .params("goods_id", mGoodsId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject data =
                                JSON.parseObject(response).getJSONObject("data");
                        initWebView(data);
                        initGoodsInfo(data);
                        initPager(data);
                        setShopCartCount(data);
                    }
                })
                .build()
                .get();
    }

    private void initGoodsInfo(JSONObject data) {
        final String goodsData = data.toJSONString();
        //  loadRootFragment(R.id.frame_goods_info, GoodsInfoDelegate.create(goodsData));
    }

    private void initWebView(JSONObject data) {
        final String url = data.getString("banners");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(url);
        /*mBanner
                .setPages(new HolderCreator(), images)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);*/
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onAnimationEnd() {
        YoYo.with(new ScaleUpAnimator())
                .duration(500)
                .playOn(mIconShopCart);
        RestClient.builder()
                .url("add_shop_cart_count.php")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        LatteLogger.json("ADD", response);
                        final boolean isAdded = JSON.parseObject(response).getBoolean("data");
                        if (isAdded) {
                            mShopCount++;
                            mCircleTextView.setVisibility(View.VISIBLE);
                            mCircleTextView.setText(String.valueOf(mShopCount));
                        }
                    }
                })
                .params("count", mShopCount)
                .build()
                .post();
    }
}
