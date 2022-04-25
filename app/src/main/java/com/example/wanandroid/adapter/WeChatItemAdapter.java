package com.example.wanandroid.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseAdapter;
import com.example.wanandroid.base.BaseViewHolder;
import com.example.wanandroid.bean.QuestionBean;
import com.example.wanandroid.bean.WechatItemBean;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeChatItemAdapter extends BaseAdapter<WechatItemBean.DataDTO.DatasDTO, WeChatItemAdapter.QuestionViewHolder> {
    public WeChatItemAdapter(Context context) {
        super(context, R.layout.item_home);
    }


    @Override
    public void cover(RecyclerView.ViewHolder holder, int position, WechatItemBean.DataDTO.DatasDTO datasDTO) {
        if (holder instanceof QuestionViewHolder) {
            QuestionViewHolder articleViewHolder = (QuestionViewHolder) holder;
            String username = datasDTO.getAuthor().isEmpty() ? datasDTO.getShareUser() : datasDTO.getAuthor();
            articleViewHolder.article_author.setText(username);
            articleViewHolder.article_chapterName.setText(datasDTO.getSuperChapterName() + "/" + datasDTO.getChapterName());
            articleViewHolder.article_data.setText(datasDTO.getNiceDate());
            articleViewHolder.article_title.setText(Html.fromHtml(datasDTO.getTitle()));

            if (datasDTO.getEnvelopePic().isEmpty()) {
                articleViewHolder.article_thumbnail.setVisibility(View.GONE);
            } else {
                articleViewHolder.article_thumbnail.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(datasDTO.getEnvelopePic()).centerCrop().into(articleViewHolder.article_thumbnail);
            }

            if (datasDTO.isFresh()) {
                articleViewHolder.article_fresh.setVisibility(View.VISIBLE);
            } else {
                articleViewHolder.article_fresh.setVisibility(View.GONE);
            }

            if (datasDTO.getTags().size() > 0) {
                articleViewHolder.article_tag.setVisibility(View.VISIBLE);
                WechatItemBean.DataDTO.DatasDTO.TagsDTO tag = datasDTO.getTags().get(0);
                articleViewHolder.article_tag.setText(tag.getName());
            } else {
                articleViewHolder.article_tag.setVisibility(View.GONE);
            }


            articleViewHolder.itemView.setOnClickListener(v -> {
                if (mItemClick != null) {
                    mItemClick.click(datasDTO.getLink());
                }
            });
        }

    }

    @Override
    public Class<QuestionViewHolder> getViewHolderClass() {
        return QuestionViewHolder.class;
    }


    public static class QuestionViewHolder extends BaseViewHolder {
        private final ImageView like;
        private final TextView article_author;
        private final TextView article_data;
        private final TextView article_title;
        private final TextView article_chapterName;
        private final ImageView article_thumbnail;
        private final TextView article_fresh;
        private final TextView article_top;
        private final TextView article_tag;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            like = itemView.findViewById(R.id.iv_like);
            article_author = itemView.findViewById(R.id.tv_article_author);
            article_data = itemView.findViewById(R.id.tv_article_date);
            article_title = itemView.findViewById(R.id.tv_article_title);
            article_chapterName = itemView.findViewById(R.id.tv_article_chapterName);
            article_thumbnail = itemView.findViewById(R.id.iv_article_thumbnail);
            article_fresh = itemView.findViewById(R.id.tv_article_fresh);
            article_top = itemView.findViewById(R.id.tv_article_top);
            article_tag = itemView.findViewById(R.id.tv_article_tag);
        }
    }


}
