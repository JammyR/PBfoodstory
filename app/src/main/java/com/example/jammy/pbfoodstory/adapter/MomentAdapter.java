package com.example.jammy.pbfoodstory.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jammy.pbfoodstory.R;

import java.util.List;

/**
 * Created by Jammy on 2017/9/6.
 */

public class MomentAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public MomentAdapter (int layoutResId,List data){
        super(layoutResId,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Glide.with(mContext).load(item).centerCrop().into((ImageView) helper.getView(R.id.iv));
    }


    /**
     * 加载方法实例
     */
//    @Override
//    protected void convert(BaseViewHolder helper, HomeItem item) {
//        helper.setText(R.id.text, item.getTitle());
//        helper.setImageResource(R.id.icon, item.getImageResource());
//        // 加载网络图片
//        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) viewHolder.getView(R.id.iv));
//    }


}
