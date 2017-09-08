package com.example.jammy.pbfoodstory.photo;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jammy.pbfoodstory.R;
import com.example.jammy.pbfoodstory.adapter.PhotoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity {

    PhotoAdapter adapter;
    List<String> list;

    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        list = getIntent().getStringArrayListExtra("list");
        adapter = new PhotoAdapter(list,this);
        vp.setAdapter(adapter);
    }
}
