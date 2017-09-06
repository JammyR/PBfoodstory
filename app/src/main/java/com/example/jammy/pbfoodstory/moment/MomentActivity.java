package com.example.jammy.pbfoodstory.moment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jammy.pbfoodstory.R;
import com.example.jammy.pbfoodstory.adapter.MomentAdapter;
import com.example.jammy.pbfoodstory.utils.SpaceItemDecoration;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MomentActivity extends AppCompatActivity {

    @BindView(R.id.et_moment)
    EditText etMoment;
    @BindView(R.id.rv)
    RecyclerView rv;
    List picList;
    MomentAdapter adapter;
    public static int REQUEST_CODE_CHOOSE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monent);
        ButterKnife.bind(this);

        picList = new ArrayList();
        adapter = new MomentAdapter(R.layout.item_moment,picList);
        //// TODO: 2017/9/6 初始化加号要改
        picList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504697405166&di=47cec865c413f36b1e247979af029108&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fbaike%2Fpic%2Fitem%2Fa08b87d6277f9e2fda25102e1d30e924b899f380.jpg");
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.addItemDecoration(new SpaceItemDecoration(10));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Matisse.from(MomentActivity.this)
                        .choose(MimeType.allOf())
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(9)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);// 设置作为标记的请求码
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            for(Uri uri : Matisse.obtainResult(data))
            picList.add(uri.toString());
            adapter.setNewData(picList);
        }
    }

}
