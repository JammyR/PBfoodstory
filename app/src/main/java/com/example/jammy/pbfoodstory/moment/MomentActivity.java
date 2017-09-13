package com.example.jammy.pbfoodstory.moment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jammy.pbfoodstory.R;
import com.example.jammy.pbfoodstory.adapter.MomentAdapter;
import com.example.jammy.pbfoodstory.address.AddressActivity;
import com.example.jammy.pbfoodstory.bean.Moment;
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
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class MomentActivity extends AppCompatActivity {

    PoiItem poiItem;
    public static final int REQUEST_CODE_ADDRESS_SUCCESS = 1000;
    @BindView(R.id.rv)
    RecyclerView rv;
    List<String> picList;
    MomentAdapter adapter;
    public static int REQUEST_CODE_CHOOSE = 1;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    public static int REQUEST_CODE_ADDRESS = 2;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    ActionBar actionBar;
    @BindView(R.id.et_moment)
    EditText etMoment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monent);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        actionBar.setTitle("发布动态");
        actionBar.setDisplayHomeAsUpEnabled(true);

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
                } else {
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
            for (Uri uri : Matisse.obtainResult(data)) {
                picList.add(uri.toString());
            }
            adapter.setNewData(picList);
        } else if (requestCode == REQUEST_CODE_ADDRESS && resultCode == REQUEST_CODE_ADDRESS_SUCCESS) {
            poiItem = data.getParcelableExtra("result");
            tvAddress.setText(poiItem.getTitle());
        }
    }

    @OnClick({R.id.tv_address, R.id.iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                /**
                 * todo:跳轉至AddressActivity進行具體地址選擇
                 */

                startActivityForResult(new Intent(MomentActivity.this, AddressActivity.class), REQUEST_CODE_ADDRESS);


                break;
            case R.id.iv:
                //todo:佈局優化
//                tvAddress.setText(String.valueOf(MainActivity.location.getExtras().get("Address")));
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.moment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                List<String> tempList = new ArrayList(picList);
                tempList.remove(0);
                String[] temp = tempList.toArray(new String[tempList.size()]);
                BmobFile.uploadBatch(temp, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        Moment moment = new Moment();
                        moment.setDescribe(etMoment.getText().toString());
                        moment.setPoiItem(poiItem);
                        moment.setStarNum(ratingBar.getRating());
                        moment.setPicList(list1);
                        moment.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                MomentActivity.this.finish();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {

                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
