package com.example.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.FragmentAdapter;
import com.example.wanandroid.R;
import com.example.wanandroid.fragment.WechatFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTabFragment extends Fragment {
    public TabLayout tabLayout;
    public ViewPager viewPager;
    public CustomAdapter adapter;

    public ArrayList<Fragment> fragments = new ArrayList<>();
    public ArrayList<String> tabs = new ArrayList<>();



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

    protected abstract void initData();

    protected  void initView(View view){
        tabLayout = view.findViewById(R.id.wechat_tablayout);
        viewPager = view.findViewById(R.id.wechat_viewpager);
        adapter = new CustomAdapter(getFragmentManager(), fragments, tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class CustomAdapter extends FragmentAdapter {

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
