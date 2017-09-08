package com.example.jammy.pbfoodstory.moment;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jammy.pbfoodstory.R;
import com.example.jammy.pbfoodstory.adapter.MomentAdapter;
import com.example.jammy.pbfoodstory.photo.PhotoActivity;
import com.example.jammy.pbfoodstory.utils.SpaceItemDecoration;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MomentActivity extends AppCompatActivity{

    PoiSearch poiSearch;

    @BindView(R.id.et_moment)
    EditText etMoment;
    @BindView(R.id.rv)
    RecyclerView rv;
    List<String> picList;
    MomentAdapter adapter;
    public static int REQUEST_CODE_CHOOSE = 1;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    Location location ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monent);
        ButterKnife.bind(this);

        picList = new ArrayList();
        adapter = new MomentAdapter(R.layout.item_moment, picList);
        //// TODO: 2017/9/6 初始化加号要改，想辦法把mipmap的文件添加進去到第一個
        picList.add("https://raw.githubusercontent.com/Datartvinci/BuyMeSth/master/base/src/main/res/drawable/ic_add.png");
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.addItemDecoration(new SpaceItemDecoration(20));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    Matisse.from(MomentActivity.this)
                            .choose(MimeType.allOf())
                            .theme(R.style.Matisse_Dracula)
                            .countable(false)
                            .maxSelectable(9)
                            .imageEngine(new GlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);// 设置作为标记的请求码
                }
                else{
                    //todo:打开已选图片浏览
                    Intent intent = new Intent(MomentActivity.this, PhotoActivity.class);
                    intent.putStringArrayListExtra("list", (ArrayList<String>) picList);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            for (Uri uri : Matisse.obtainResult(data))
                picList.add(uri.toString());
            adapter.setNewData(picList);
        }
    }

    @OnClick(R.id.tv_address)
    public void onViewClicked() {
        /**
         * todo:點擊進行地址選擇
         */
    }
}
