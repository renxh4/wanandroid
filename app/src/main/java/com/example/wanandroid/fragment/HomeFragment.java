package com.example.wanandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.Utils;
import com.example.wanandroid.activity.WebviewActivity;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.ArticleTopBean;
import com.example.wanandroid.adapter.CustomLinearLayoutManager;
import com.example.wanandroid.adapter.SpaceItemDecoration;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleData;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.net.Api;
import com.example.wanandroid.net.OkhttpManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private final String mText;
    private ArticleAdapter articleAdapter;
    private boolean needRefresh;
    private int mCurrenPagerCount;
    private int pageCount;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

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
        swipeRefreshLayout = view.findViewById(R.id.top_refresh);
        recyclerView = view.findViewById(R.id.recycle);

        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        articleAdapter = new ArticleAdapter(getContext());
        //设置Adapter
        recyclerView.setAdapter(articleAdapter);
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
                    Log.d("mmm", "到达了底部");
                    refreshData();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("mmm", "top 刷新");
                articleAdapter.clear();
                needRefresh=false;
                initData();
            }
        });

        articleAdapter.setItemClick(new ArticleAdapter.ItemClick() {
            @Override
            public void click(String url) {
                Log.d("mmmurl11", url);
                WebviewActivity.start(getContext(),url);

            }
        });
    }

    private void refreshData() {
        if (needRefresh) {
            String getArticle = Api.BASE + "/article/list/%s/json";
            if (++mCurrenPagerCount < pageCount) {
                String format = String.format(getArticle, mCurrenPagerCount);
                Log.d("mmmurl", format);
                OkhttpManager.INSTANCE.get(format, new OkhttpManager.CallBack() {
                    @Override
                    public void success(String json) {
                        ArticleBean articleBean = new Gson().fromJson(json, ArticleBean.class);
                        List<ArticleBean.DataDTO.DatasDTO> datas = articleBean.getData().getDatas();
                        ArrayList<ArticleData> articleDatas = new ArrayList<>();
                        for (ArticleBean.DataDTO.DatasDTO datasDTO : datas) {
                            ArticleData articleData = new ArticleData();
                            articleData.articleData = datasDTO;
                            articleDatas.add(articleData);
                        }
                        articleAdapter.setData(articleDatas);
                        Utils.executeOnMainThread(() -> {
                            articleAdapter.notifyDataSetChanged();
                        });

                    }

                    @Override
                    public void fail(String msg) {

                    }
                });
            }

        }
    }


    private void initData() {
        getTopArticle();
    }

    private void getTopArticle() {
        OkhttpManager.INSTANCE.get(Api.getTop, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                ArticleTopBean articleBean = new Gson().fromJson(json, ArticleTopBean.class);
                List<ArticleBean.DataDTO.DatasDTO> data = articleBean.getData();
                ArrayList<ArticleData> articleDatas = new ArrayList<>();
                for (ArticleBean.DataDTO.DatasDTO datasDTO : data) {
                    datasDTO.top="1";
                    ArticleData articleData = new ArticleData();
                    articleData.articleData = datasDTO;
                    articleDatas.add(articleData);
                }
                articleAdapter.setData(articleDatas);
                getArticle();
            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    private void getArticle() {
        OkhttpManager.INSTANCE.get(Api.getArticle, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                ArticleBean articleBean = new Gson().fromJson(json, ArticleBean.class);
                pageCount = articleBean.getData().getPageCount();
                List<ArticleBean.DataDTO.DatasDTO> datas = articleBean.getData().getDatas();
                ArrayList<ArticleData> articleDatas = new ArrayList<>();
                for (ArticleBean.DataDTO.DatasDTO datasDTO : datas) {
                    ArticleData articleData = new ArticleData();
                    articleData.articleData = datasDTO;
                    articleDatas.add(articleData);
                }
                articleAdapter.setData(articleDatas);
                getBanner();
            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    private void getBanner() {
        OkhttpManager.INSTANCE.get(Api.getBanner, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                BannerBean bannerBean = new Gson().fromJson(json, BannerBean.class);
                List<BannerBean.DataDTO> data = bannerBean.getData();
                ArticleData articleData = new ArticleData();
                articleData.bannerData = data;
                articleAdapter.setDataFirst(articleData);
                Utils.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        articleAdapter.notifyDataSetChanged();
                        needRefresh = true;
                        mCurrenPagerCount = 0;
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }

            @Override
            public void fail(String msg) {

            }
        });
    }
}
