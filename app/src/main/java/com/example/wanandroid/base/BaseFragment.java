package com.example.wanandroid.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.activity.WebviewActivity;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.CustomLinearLayoutManager;
import com.example.wanandroid.adapter.QuestionAdapter;
import com.example.wanandroid.adapter.SpaceItemDecoration;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class BaseFragment extends Fragment {

    private RecyclerView recyclerView;
    public SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        onCreate();
        //设置Adapter
        initAdapter();

    }

    protected  void initAdapter(){
        recyclerView.setAdapter(getAdapter());
        getAdapter().setItemClick(new ArticleAdapter.ItemClick() {
            @Override
            public void click(String url) {
                itemClick(url);
            }
        });
    };

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle);
        swipeRefreshLayout = view.findViewById(R.id.top_refresh);

        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置分隔线
        recyclerView.addItemDecoration(new SpaceItemDecoration(getContext()));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initEvent();
    }


    private void initEvent() {
        //recyclerView.canScrollVertically(1)的值表示是否能向上滚动，false表示已经滚动到底部
        //recyclerView.canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    bottomRefresh();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                topRefresh();
            }
        });


    }

    public void setProgressStatus(boolean refresh){
        swipeRefreshLayout.setRefreshing(refresh);
    }

    public abstract int getLayout();

    public abstract BaseAdapter getAdapter();

    public abstract void topRefresh();

    public abstract void bottomRefresh();

    public abstract void onCreate();

    public abstract void itemClick(String url);
}
