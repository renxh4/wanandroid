package com.example.wanandroid.fragment;

import android.util.Log;

import com.example.wanandroid.R;
import com.example.wanandroid.Utils;
import com.example.wanandroid.activity.WebviewActivity;
import com.example.wanandroid.adapter.QuestionAdapter;
import com.example.wanandroid.base.BaseAdapter;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.bean.QuestionBean;
import com.example.wanandroid.net.Api;
import com.example.wanandroid.net.OkhttpManager;
import com.google.gson.Gson;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionFragment extends BaseFragment {

    private QuestionAdapter questionAdapter;
    private int page = 1;

    public static QuestionFragment newInstance() {
        return new QuestionFragment();
    }

    @Override
    public void onCreate() {
        questionAdapter = new QuestionAdapter(getContext());
        initData(false, true);
    }

    @Override
    public void itemClick(String url) {
        WebviewActivity.start(getContext(),url);
    }

    private void initData(boolean add, boolean refresh) {
        if (add) {
            page++;
        }
        if (refresh) {
            questionAdapter.clear();
            page = 1;
        }
        String url = String.format(Api.getQuestion, page);
        OkhttpManager.INSTANCE.get(url, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                QuestionBean questionBean = new Gson().fromJson(json, QuestionBean.class);
                List<QuestionBean.DataDTO.DatasDTO> datas = questionBean.getData().getDatas();
                questionAdapter.setData(datas);
                Log.d("mmm",url+"///"+datas.size());
                Utils.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        questionAdapter.notifyDataSetChanged();
                        setProgressStatus(false);
                    }
                });

            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.home_fragmet;
    }

    @Override
    public BaseAdapter<QuestionBean.DataDTO.DatasDTO, QuestionAdapter.QuestionViewHolder> getAdapter() {
        return questionAdapter;
    }

    @Override
    public void topRefresh() {
        initData(false, true);
    }

    @Override
    public void bottomRefresh() {
        initData(true, false);
    }

}
