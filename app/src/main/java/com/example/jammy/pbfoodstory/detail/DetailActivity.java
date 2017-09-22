package com.example.jammy.pbfoodstory.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jammy.pbfoodstory.R;
import com.example.jammy.pbfoodstory.adapter.MomentAdapter;
import com.example.jammy.pbfoodstory.bean.Moment;
import com.example.jammy.pbfoodstory.moment.MomentActivity;
import com.example.jammy.pbfoodstory.photo.PhotoActivity;
import com.example.jammy.pbfoodstory.utils.SpaceItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;

public class DetailActivity extends AppCompatActivity {


    MomentAdapter adapter;
    Moment moment;
    @BindView(R.id.et_moment)
    EditText etMoment;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String json = getIntent().getStringExtra("marker");
        Gson gson = new Gson();
        moment = gson.fromJson(json, Moment.class);

        ratingBar.setRating(moment.getStarNum());
        tvAddress.setText(moment.getPoiItem().getTitle());
        etMoment.setText("当时的心情："+moment.getDescribe());

        adapter = new MomentAdapter(R.layout.item_moment, moment.getPicList());
        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.addItemDecoration(new SpaceItemDecoration(20));
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    //todo:打开已选图片浏览
                    Intent intent = new Intent(DetailActivity.this, PhotoActivity.class);
                    intent.putStringArrayListExtra("list", (ArrayList<String>) moment.getPicList());
                    startActivity(intent);
            }
        });

    }
}
