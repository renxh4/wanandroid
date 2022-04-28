package com.example.wanandroid.fragment;


import com.example.wanandroid.Utils;
import com.example.wanandroid.base.BaseTabFragment;
import com.example.wanandroid.bean.WechatBean;
import com.example.wanandroid.net.Api;
import com.example.wanandroid.net.OkhttpManager;
import com.google.gson.Gson;

import java.util.List;


public class WechatFragment extends BaseTabFragment {

    public static WechatFragment newInstance() {
        return new WechatFragment();
    }

    public void initData() {
        OkhttpManager.INSTANCE.get(Api.getWechat, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                WechatBean wechatBean = new Gson().fromJson(json, WechatBean.class);
                List<WechatBean.DataDTO> data = wechatBean.getData();
                for (WechatBean.DataDTO dataDTO : data) {
                    tabs.add(dataDTO.getName());
                }

                Utils.executeOnMainThread(() -> {
                    for (int i = 0; i < data.size(); i++) {
                        tabLayout.addTab(tabLayout.newTab().setText(data.get(i).getName()));
                        int id = data.get(i).getId();
                        fragments.add(WeChatItemFragment.newInstance(id));
                    }
                    adapter.notifyDataSetChanged();
                    viewPager.setOffscreenPageLimit(data.size());
                });

            }

            @Override
            public void fail(String msg) {

            }
        });
    }


}
