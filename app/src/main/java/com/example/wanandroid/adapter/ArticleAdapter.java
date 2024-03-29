package com.example.wanandroid.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.ArticleBean;
import com.example.wanandroid.bean.ArticleData;
import com.example.wanandroid.bean.BannerBean;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Context mContext;
    private ArrayList<ArticleData> mDatas = new ArrayList<>();

    private final int HEADER = 0;
    private final int NORMALE = 1;
    private final int FOOT = 2;
    private ItemClick mItemClick;

    public ArticleAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == NORMALE) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home, parent, false);
            holder = new ArticleViewHolder(inflate);
        } else if (viewType == HEADER) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home_banner, parent, false);
            holder = new BannerViewHolder(inflate);
        } else if (viewType == FOOT) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home_foot, parent, false);
            holder = new FootViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_home_foot, parent, false);
            holder = new ArticleViewHolder(inflate);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == HEADER) {
            if (mDatas != null && mDatas.size() > 0) {
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                ArticleData articleData = mDatas.get(position);
                List<BannerBean.DataDTO> bannerData = articleData.bannerData;
                initBanner(bannerData, bannerViewHolder);
            }
        } else if (itemViewType == NORMALE) {
            ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
            ArticleData articleData = mDatas.get(position);
            ArticleBean.DataDTO.DatasDTO datasDTO = articleData.articleData;
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
                ArticleBean.DataDTO.Tag tag = datasDTO.getTags().get(0);
                articleViewHolder.article_tag.setText(tag.name);
            } else {
                articleViewHolder.article_tag.setVisibility(View.GONE);
            }

            if (datasDTO.top != null && datasDTO.top.equals("1")) {
                articleViewHolder.article_top.setVisibility(View.VISIBLE);
            } else {
                articleViewHolder.article_top.setVisibility(View.GONE);
            }

            articleViewHolder.itemView.setOnClickListener(v -> {
                if (mItemClick != null) {
                    mItemClick.click(datasDTO.getLink());
                }
            });
        }
    }

    private void initBanner(List<BannerBean.DataDTO> bannerData, BannerViewHolder bannerViewHolder) {
        if (bannerData == null) return;
        BannerAdapter bannerAdapter = new BannerAdapter(bannerData, mContext, bannerViewHolder.viewPager);
        bannerViewHolder.viewPager.setAdapter(bannerAdapter);
        bannerViewHolder.title.setText(bannerData.get(0).getTitle());
        bannerViewHolder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                 /* 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到
                调用。其中三个参数的含义分别为：
                arg0 :当前页面，及你点击滑动的页面
                arg1:当前页面偏移的百分比
                arg2:当前页面偏移的像素位置*/
            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转完后得到调用，arg0是你当前选中的页面的Position
                int pos = position % bannerData.size();
                bannerViewHolder.title.setText(bannerData.get(pos).getTitle());
                bannerAdapter.mCurrentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //状态改变的时候调用  arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

            }
        });
    }


    @Override
    public int getItemCount() {
        return mDatas.size() == 0 ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else if (position == mDatas.size()) {
            return FOOT;
        } else {
            return NORMALE;
        }
    }

    public void setData(ArrayList<ArticleData> datas) {
        mDatas.addAll(datas);
    }

    public void setDataFirst(ArticleData data) {
        mDatas.add(0, data);
    }

    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
        }
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ImageView like;
        private final TextView article_author;
        private final TextView article_data;
        private final TextView article_title;
        private final TextView article_chapterName;
        private final ImageView article_thumbnail;
        private final TextView article_fresh;
        private final TextView article_top;
        private final TextView article_tag;

        public ArticleViewHolder(@NonNull View itemView) {
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

    static class BannerViewHolder extends RecyclerView.ViewHolder {

        private final ViewPager viewPager;
        private final TextView title;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.banner);
            title = itemView.findViewById(R.id.banner_title);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    public void setItemClick(ItemClick itemClick) {
        this.mItemClick = itemClick;
    }

    public interface ItemClick {
        void click(String url);
    }


}
