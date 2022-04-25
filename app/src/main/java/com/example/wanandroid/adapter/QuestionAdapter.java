package com.example.wanandroid.adapter;

import android.content.Context;
import android.view.View;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseAdapter;
import com.example.wanandroid.base.BaseViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionAdapter extends BaseAdapter<String, QuestionAdapter.QuestionViewHolder> {
    public QuestionAdapter(Context context) {
        super(context, R.layout.item_home);
    }


    @Override
    public void cover(RecyclerView.ViewHolder holder, int position, String s) {

    }

    @Override
    public Class<QuestionViewHolder> getViewHolderClass() {
        return QuestionViewHolder.class;
    }


    public static class QuestionViewHolder extends BaseViewHolder {
        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
