package com.example.wanandroid.fragment;

import com.example.wanandroid.Utils;
import com.example.wanandroid.activity.WebviewActivity;
import com.example.wanandroid.adapter.WeChatItemAdapter;
import com.example.wanandroid.base.BaseAdapter;
import com.example.wanandroid.base.BaseFragment;
import com.example.wanandroid.bean.WechatItemBean;
import com.example.wanandroid.net.Api;
import com.example.wanandroid.net.OkhttpManager;
import com.google.gson.Gson;

import java.util.List;

public class WeChatItemFragment extends BaseFragment {

    private final int id;
    private int pager = 1;
    private WeChatItemAdapter weChatItemAdapter;

    public WeChatItemFragment(int id) {
        this.id = id;
    }

    public static WeChatItemFragment newInstance(int id) {
        return new WeChatItemFragment(id);
    }

    @Override
    public void onCreate() {
        weChatItemAdapter = new WeChatItemAdapter(getContext());
        initData(false, true);
    }

    private void initData(boolean add, boolean refresh) {
        if (add) {
            pager++;
        }
        if (refresh) {
            weChatItemAdapter.clear();
            pager = 1;
        }
        String format = String.format(Api.getWechatitem, id, pager);
        OkhttpManager.INSTANCE.get(format, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                WechatItemBean wechatItemBean = new Gson().fromJson(json, WechatItemBean.class);
                List<WechatItemBean.DataDTO.DatasDTO> datas = wechatItemBean.getData().getDatas();
                weChatItemAdapter.setData(datas);
                Utils.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        weChatItemAdapter.notifyDataSetChanged();
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
    public BaseAdapter getAdapter() {
        return weChatItemAdapter;
    }

    @Override
    public void topRefresh() {
        initData(false, true);
    }

    @Override
    public void bottomRefresh() {
        initData(true, false);
    }


    @Override
    public void itemClick(String url) {
        WebviewActivity.start(getContext(), url);
    }
}
