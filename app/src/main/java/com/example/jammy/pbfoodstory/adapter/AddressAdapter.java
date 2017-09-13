package com.example.jammy.pbfoodstory.adapter;

import android.support.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jammy.pbfoodstory.R;

import java.util.List;

/**
 * Created by Jammy on 2017/9/13.
 */

public class AddressAdapter extends BaseQuickAdapter<PoiItem,BaseViewHolder>{
    public AddressAdapter(@Nullable List<PoiItem> data) {
        super(R.layout.item_address,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_address,item.getTitle()).setText(R.id.tv_stress,item.getProvinceName()+item.getCityName()+item.getAdName()+item.getBusinessArea()+item.getSnippet());
        helper.setText(R.id.tv_distance,item.getDistance()+"m");

    }
}