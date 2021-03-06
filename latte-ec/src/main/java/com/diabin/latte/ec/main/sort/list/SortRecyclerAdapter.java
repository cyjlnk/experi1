package com.diabin.latte.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.diabin.latte.delegates.LatteDelegate;
import com.diabin.latte.ec.R;
import com.diabin.latte.ec.main.sort.SortDelegate;
import com.diabin.latte.ec.main.sort.content.ContentDelegate;
import com.diabin.latte.ui.recycler.ItemType;
import com.diabin.latte.ui.recycler.MultIpleViewHolder;
import com.diabin.latte.ui.recycler.MultipleFields;
import com.diabin.latte.ui.recycler.MultipleItemEntity;
import com.diabin.latte.ui.recycler.MultipleRecyclerAdapter;

import java.util.List;

/**
 * Created by fei on 2017/8/3.
 */

public class SortRecyclerAdapter extends MultipleRecyclerAdapter {

    private final SortDelegate DELEGATE;
    private int mPreposition=0;

    protected SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE=delegate;
        //添加垂直菜单布局
        addItemType(ItemType.VERTICAL_MEUN_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultIpleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.VERTICAL_MEUN_LIST:
                final String text=entity.getField(MultipleFields.TEXT);
                final boolean isClicked=entity.getField(MultipleFields.TAG);
                final AppCompatTextView name=holder.getView(R.id.tv_vertical_item_name);
                final View line=holder.getView(R.id.view_line);
                final View itemView=holder.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPosition=holder.getAdapterPosition();
                        if(mPreposition!=currentPosition){
                            //还原上一个
                            getData().get(mPreposition).setField(MultipleFields.TAG,false);
                            notifyItemChanged(mPreposition);
                            //更新选中的item
                            entity.setField(MultipleFields.TAG,true);
                            notifyItemChanged(currentPosition);
                            mPreposition=currentPosition;

                            final int contenId=getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contenId);

                        }
                    }
                });

                if(!isClicked){
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,
                            R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.item_backgrount));

                }else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext,R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }

                holder.setText(R.id.tv_vertical_item_name,text);

                break;
            default:
                break;
        }
    }

    private void showContent(int contentId){
        final ContentDelegate delegate=ContentDelegate.newInstance(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate){
        final LatteDelegate contentDelegate=DELEGATE.findChildFragment(ContentDelegate.class);
        if(contentDelegate!=null){
            contentDelegate.replaceFragment(delegate,false);
        }
    }

}
