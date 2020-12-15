package com.example.war.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.war.R;
import com.victor.loading.rotate.RotateLoading;

public class Fragment_TopTen extends Fragment_Base_With_Sound {
    private RotateLoading top_ten_RotateLoading;
    private Fragment_Map fragment_map;
    private Fragment_List fragment_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_ten, container, false);
        findViews(view);
        initViews();
        if (savedInstanceState == null) {
            playSound(R.raw.top_ten, false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        top_ten_RotateLoading.stop();
    }

    private void initViews() {
        this.top_ten_RotateLoading.start();
        this.fragment_list = new Fragment_List();
        getChildFragmentManager().beginTransaction().replace(R.id.top_ten_LAY_list, this.fragment_list).commit();
        this.fragment_map = new Fragment_Map();
        getChildFragmentManager().beginTransaction().replace(R.id.top_ten_LAY_map, this.fragment_map).commit();
    }

    private void findViews(View view) {
        this.top_ten_RotateLoading = view.findViewById(R.id.top_ten_loader);
    }
}
