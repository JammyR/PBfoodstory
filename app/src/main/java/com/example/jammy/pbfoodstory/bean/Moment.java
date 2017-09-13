package com.example.jammy.pbfoodstory.bean;

import com.amap.api.services.core.PoiItem;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Jammy on 2017/9/13.
 */

public class Moment extends BmobObject{
    private String describe;
    private List<String> picList;
    private float starNum;
    private PoiItem poiItem;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public float getStarNum() {
        return starNum;
    }

    public void setStarNum(float starNum) {
        this.starNum = starNum;
    }

    public PoiItem getPoiItem() {
        return poiItem;
    }

    public void setPoiItem(PoiItem poiItem) {
        this.poiItem = poiItem;
    }
}
