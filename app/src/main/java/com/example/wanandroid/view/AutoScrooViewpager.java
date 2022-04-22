package com.example.wanandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.wanandroid.adapter.BannerAdapter;

public class AutoScrooViewpager extends ViewPager {
    public AutoScrooViewpager(@NonNull Context context) {
        super(context);
    }

    public AutoScrooViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PagerAdapter adapter = getAdapter();
        if (adapter instanceof BannerAdapter) {
            BannerAdapter bannerAdapter = (BannerAdapter) adapter;
            bannerAdapter.detach();
        }

    }
}
