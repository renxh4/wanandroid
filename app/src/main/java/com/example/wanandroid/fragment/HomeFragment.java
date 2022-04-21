package com.example.wanandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.Utils;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.SpaceItemDecoration;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.net.Api;
import com.example.wanandroid.net.OkhttpManager;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {

    private final String mText;
    private ArticleAdapter articleAdapter;

    private HomeFragment(String text) {
        this.mText = text;
    }

    public static HomeFragment newInstance(String text) {
        return new HomeFragment(text);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragmet, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(getContext());
        //设置Adapter
        recyclerView.setAdapter(articleAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new SpaceItemDecoration(getContext()));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        OkhttpManager.INSTANCE.get(Api.getArticle, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                ArticleBean articleBean = new Gson().fromJson(json, ArticleBean.class);
                Utils.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        articleAdapter.setData(articleBean.getData().getDatas());
                    }
                });
            }

            @Override
            public void fail(String msg) {

            }
        });
    }
}
