package com.example.wanandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.FragmentAdapter;
import com.example.wanandroid.R;
import com.example.wanandroid.Utils;
import com.example.wanandroid.bean.WechatBean;
import com.example.wanandroid.net.Api;
import com.example.wanandroid.net.OkhttpManager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class WechatFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> tabs = new ArrayList<>();
    private CustomAdapter adapter;


    public static WechatFragment newInstance() {
        return new WechatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragmet_wechat, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }


    private void initView(View view) {
        tabLayout = view.findViewById(R.id.wechat_tablayout);
        viewPager = view.findViewById(R.id.wechat_viewpager);
        adapter = new CustomAdapter(getFragmentManager(), fragments, tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
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


    static class CustomAdapter extends FragmentAdapter {

        private final List<Fragment> mFragments;
        private final ArrayList<String> tabs;

        public CustomAdapter(FragmentManager fragmentManager, List<Fragment> mFragments, ArrayList<String> tabs) {
            super(fragmentManager, mFragments);
            this.mFragments = mFragments;
            this.tabs = tabs;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
