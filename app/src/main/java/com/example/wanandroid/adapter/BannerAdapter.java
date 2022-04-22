package com.example.wanandroid.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.wanandroid.bean.BannerBean;

import java.util.List;


public class BannerAdapter extends PagerAdapter {

    private final List<BannerBean.DataDTO> mDatas;
    private final Context mContext;
    private final int AUTO = 0;
    private final long delytime = 5000;
    private final ViewPager mViewPager;
    public int mCurrentPos = 0;
    private final Handler mHander = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            mViewPager.setCurrentItem(++mCurrentPos);
            mHander.sendEmptyMessageDelayed(AUTO, delytime);
        }
    };

    public BannerAdapter(List<BannerBean.DataDTO> bannerData, Context context, ViewPager viewPager) {
        this.mDatas = bannerData;
        this.mContext = context;
        this.mViewPager = viewPager;
        mHander.sendEmptyMessageDelayed(AUTO, delytime);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int pos = position % mDatas.size();
        ImageView imageView = new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        Glide.with(mContext).load(mDatas.get(pos).getImagePath()).centerCrop().into(imageView);
        container.addView(imageView, layoutParams);
        return imageView;
    }

    public void detach() {
        mHander.removeCallbacksAndMessages(null);
    }

}
