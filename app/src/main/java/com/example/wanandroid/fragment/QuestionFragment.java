package com.example.wanandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.CustomLinearLayoutManager;
import com.example.wanandroid.adapter.QuestionAdapter;
import com.example.wanandroid.adapter.SpaceItemDecoration;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionFragment extends Fragment {


    private RecyclerView recyclerView;

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragmet, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle);

        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        QuestionAdapter questionAdapter = new QuestionAdapter(getContext());
        //设置Adapter
        recyclerView.setAdapter(questionAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new SpaceItemDecoration(getContext()));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<String> strings = new ArrayList<>();
        strings.add("sdkald");
        strings.add("weqwe");
        questionAdapter.setData(strings);
    }
}
