package com.example.wanandroid.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleData;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int NORMALE = 1;
    public final int FOOT = 2;
    private final Context mContext;
    private final ArrayList<T> mDatas;
    private final int mLayout;

    public BaseAdapter(Context context, int layout) {
        this.mContext = context;
        mDatas = new ArrayList<>();
        this.mLayout = layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == NORMALE) {
            View inflate = LayoutInflater.from(mContext).inflate(mLayout, parent, false);
            holder = newInstance(getViewHolderClass(), inflate);
        } else if (viewType == FOOT) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home_foot, parent, false);
            holder = new FootViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home_foot, parent, false);
            holder = new FootViewHolder(inflate);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType==NORMALE){
            cover(holder, position, mDatas.get(position));
        }
    }

    ;

    public abstract void cover(RecyclerView.ViewHolder holder, int position, T t);


    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size() + 1;
    }

    public void setData(ArrayList<T> datas) {
        mDatas.addAll(datas);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mDatas.size()) {
            return FOOT;
        } else {
            return NORMALE;
        }
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public abstract Class<VH> getViewHolderClass();

    public VH newInstance(Class<VH> tClass, View view) {
        BaseViewHolder p = null;
        try {
            Class<?> aClass = Class.forName(tClass.getName());
            Constructor<?> constructor = aClass.getConstructor(View.class);
            p = (BaseViewHolder) constructor.newInstance(view);
        } catch (Exception e) {
            Log.d("mmmex", e.getMessage());
            e.printStackTrace();
        }
        return (VH) p;
    }
}
