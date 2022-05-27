package com.example.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.TxItemBean;

import java.util.ArrayList;

public class TXAdapter extends  RecyclerView.Adapter{

    public final int NORMALE = 1;
    public final int FOOT = 2;
    public final int TITLE = 3;

    private final Context mContext;

    private ArrayList<TxItemBean> mTxItemBeans = new ArrayList<>();


    public TXAdapter(Context context){
        this. mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == NORMALE) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.tx_re_item, parent, false);
            return new TxViewHolder(inflate);
        } else if (viewType == FOOT) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home_foot, parent, false);
            return new FootViewHolder(inflate);
        } else if (viewType ==TITLE){
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.tx_re_item_title, parent, false);
            return new TitleViewHolder(inflate);
        }else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.tx_re_item, parent, false);
            return new TxViewHolder(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TxViewHolder){
            TxViewHolder holder1  = (TxViewHolder) holder;
            String name = mTxItemBeans.get(position).childrenDTO.getName();
            holder1.textView.setText(name);
        }else if (holder instanceof TitleViewHolder){
            TitleViewHolder holder1  = (TitleViewHolder) holder;
            holder1.title.setText(mTxItemBeans.get(position).name);
        }
    }

    @Override
    public int getItemCount() {
        return mTxItemBeans.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mTxItemBeans.size()) {
            return FOOT;
        } else if (!mTxItemBeans.get(position).children){
            return TITLE;
        }else {
            return NORMALE;
        }
    }

    public void addData(ArrayList<TxItemBean> txItemBeans){
        mTxItemBeans.clear();
        mTxItemBeans.addAll(txItemBeans);
        notifyDataSetChanged();
    }

    public static class TxViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public TxViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tx_item_text);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class TitleViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tx_item_title);
        }
    }
}
