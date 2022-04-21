package com.example.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {


    private final Context mContext;
    private List<ArticleBean.DataDTO.DatasDTO> mDatas;

    public ArticleAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
        ArticleViewHolder holder = new ArticleViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        ArticleBean.DataDTO.DatasDTO datasDTO = mDatas.get(position);
        holder.article_author.setText(datasDTO.getShareUser());
        holder.article_chapterName.setText(datasDTO.getSuperChapterName()+"/"+datasDTO.getChapterName());
        holder.article_data.setText(datasDTO.getNiceDate());
        holder.article_title.setText(datasDTO.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setData(List<ArticleBean.DataDTO.DatasDTO> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ImageView like;
        private final TextView article_author;
        private final TextView article_data;
        private final TextView article_title;
        private final TextView article_chapterName;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            like = itemView.findViewById(R.id.iv_like);
            article_author = itemView.findViewById(R.id.tv_article_author);
            article_data = itemView.findViewById(R.id.tv_article_date);
            article_title = itemView.findViewById(R.id.tv_article_title);
            article_chapterName = itemView.findViewById(R.id.tv_article_chapterName);
        }
    }
}
