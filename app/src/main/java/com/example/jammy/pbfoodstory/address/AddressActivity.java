package com.example.jammy.pbfoodstory.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jammy.pbfoodstory.R;
import com.example.jammy.pbfoodstory.adapter.AddressAdapter;
import com.example.jammy.pbfoodstory.main.MainActivity;
import com.example.jammy.pbfoodstory.moment.MomentActivity;
import com.example.jammy.pbfoodstory.utils.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener, BaseQuickAdapter.RequestLoadMoreListener {


    List<PoiItem> list;
    PoiSearch poiSearch;
    int currentPage = 0;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.rv)
    RecyclerView rv;

    AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        list = new ArrayList<>();

        adapter = new AddressAdapter(list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ////todo:这里添加rv的分割线
        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        rv.setAdapter(adapter);


        PoiSearch.Query query = new PoiSearch.Query("", "", "惠州");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(++currentPage);// 设置查第一页
        LatLonPoint lp = new LatLonPoint(MainActivity.location.getLatitude(), MainActivity.location.getLongitude());

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, 1000));
        // 设置搜索区域为以lp点为圆心，其周围1000米范围
        poiSearch.searchPOIAsyn();// 异步搜索

        adapter.setOnLoadMoreListener(this, rv);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("result", (PoiItem) adapter.getData().get(position));
                AddressActivity.this.setResult(MomentActivity.REQUEST_CODE_ADDRESS_SUCCESS, intent);
                finish();
            }
        });
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (poiResult.getPois().size() == 0) {
            adapter.loadMoreEnd();
        } else {
            list.addAll(poiResult.getPois());
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onLoadMoreRequested() {
        PoiSearch.Query query = new PoiSearch.Query("", "", "惠州");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(++currentPage);
        LatLonPoint lp = new LatLonPoint(MainActivity.location.getLatitude(), MainActivity.location.getLongitude());

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, 1000));
        // 设置搜索区域为以lp点为圆心，其周围1000米范围
        poiSearch.searchPOIAsyn();// 异步搜索
    }
}
