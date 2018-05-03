package com.diabin.latte.ec.main.personal;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.diabin.latte.delegates.bottom.BottomItemDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.R2;
import com.diabin.latte.ec.main.personal.list.ListAdapter;
import com.diabin.latte.ec.main.personal.list.ListBean;
import com.diabin.latte.ec.main.personal.list.ListItemType;
import com.diabin.latte.ec.main.personal.order.OrderListDelegate;
import com.diabin.latte.ec.main.personal.profile.UserProfileDelegate;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.id.list;


/**
 * Created by 傅令杰
 */

public class PersonalDelegate extends BottomItemDelegate {

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs=null;
    @BindView(R2.id.rv_personal_setting)
    RecyclerView rvSettings=null;

    @OnClick(R2.id.tv_all_order)
    void onCliokAllOrder(){
        mArgs.putString(ORDER_TYPE, "all");
        startOrderListByType();
    }
    @OnClick(R2.id.img_user_avatar)
    void onCliokAvater(){
        getParentDelegate().start(new UserProfileDelegate());
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }


    private void startOrderListByType() {
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().start(delegate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

       final ListBean address =new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setText("收货地址")
                .build();
       final ListBean setting =new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("系统设置")
                .build();
        final  List<ListBean> data=new ArrayList<>();
        data.add(address);
        data.add(setting);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        rvSettings.setAdapter(adapter);
       // rvSettings.addOnItemTouchListener(new PersonalClickListener(this));

    }


}
