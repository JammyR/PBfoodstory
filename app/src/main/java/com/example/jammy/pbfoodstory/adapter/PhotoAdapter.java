package com.example.jammy.pbfoodstory.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Jammy on 2017/9/8.
 */

public class PhotoAdapter extends PagerAdapter{

    List list ;
    Context context;
    List<View> viewList;

    public PhotoAdapter(List list,Context context){
        this.list = list;
        this.context = context;
        viewList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {/////实例化页卡用的
        /////todo:要优化
        PhotoView photoView = new PhotoView(context);
        photoView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        container.addView(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });

        Glide.with(context).load(list.get(position)).asBitmap().into(photoView);
        viewList.add(position,photoView);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {////删除页卡
        container.removeView(viewList.get(position));
    }
}
