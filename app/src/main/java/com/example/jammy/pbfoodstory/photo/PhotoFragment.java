package com.example.jammy.pbfoodstory.photo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jammy.pbfoodstory.R;

/**
 * Created by Jammy on 2017/9/8.
 */

public class PhotoFragment extends Fragment{

    ////初始化Fragment的布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo,container,false);
        return view;
    }


}
