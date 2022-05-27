package com.example.wanandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.Utils;
import com.example.wanandroid.adapter.CustomLinearLayoutManager;
import com.example.wanandroid.adapter.SpaceItemDecoration;
import com.example.wanandroid.adapter.TXAdapter;
import com.example.wanandroid.bean.TxBean;
import com.example.wanandroid.bean.TxItemBean;
import com.example.wanandroid.net.Api;
import com.example.wanandroid.net.OkhttpManager;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TXFragment extends Fragment {

    private TXAdapter adapter;
    private ArrayList<TxItemBean> txItemBeans = new ArrayList<>();

    public static TXFragment newInstance() {
        return new TXFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tx_fragmet, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    private void initData() {
        OkhttpManager.INSTANCE.get(Api.getTX, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                TxBean txBean = new Gson().fromJson(json, TxBean.class);
                List<TxBean.DataDTO> data = txBean.getData();
                for (TxBean.DataDTO dataDTO: data) {
                    TxItemBean txItemBean = new TxItemBean();
                    txItemBean.children =false;
                    txItemBean.name = dataDTO.getName();
                    txItemBeans.add(txItemBean);
                    List<TxBean.DataDTO.ChildrenDTO> children = dataDTO.getChildren();
                    for (TxBean.DataDTO.ChildrenDTO childrenDTO: children){
                        TxItemBean txItemBean1 = new TxItemBean();
                        txItemBean1.children =true;
                        txItemBean1.childrenDTO = childrenDTO;
                        txItemBeans.add(txItemBean1);
                    }
                }

                Utils.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addData(txItemBeans);
                    }
                });

            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.tx_recycle);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        //设置布局管理器
        recyclerView.setLayoutManager(flexboxLayoutManager);
        adapter = new TXAdapter(getContext());
        recyclerView.setAdapter(adapter);
    }



}
