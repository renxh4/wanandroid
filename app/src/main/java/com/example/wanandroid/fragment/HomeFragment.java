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

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.net.Api;
import com.example.wanandroid.net.OkhttpManager;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {

    private final String mText;

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
        TextView textView = view.findViewById(R.id.home_fragment_text);
        textView.setText(mText);

        initData();
    }

    private void initData() {
        OkhttpManager.INSTANCE.get(Api.getArticle, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                ArticleBean articleBean = new Gson().fromJson(json, ArticleBean.class);
                Log.d("mmm",articleBean.getData().getDatas().get(0).getLink());
            }

            @Override
            public void fail(String msg) {

            }
        });
    }
}
