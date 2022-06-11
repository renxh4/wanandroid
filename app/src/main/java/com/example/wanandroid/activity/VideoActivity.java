package com.example.wanandroid.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.wanandroid.R;
import com.example.wanandroid.Utils;
import com.example.wanandroid.adapter.CustomLinearLayoutManager;
import com.example.wanandroid.adapter.SpaceItemDecoration;
import com.example.wanandroid.adapter.WebAadpter;
import com.example.wanandroid.base.DataBean;
import com.google.gson.Gson;

public class VideoActivity extends AppCompatActivity {

    private WebAadpter adapter;
    private DataBean dataBean;
    private boolean gone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
        initData();
    }

    private void initData() {
        String s = Utils.get(this);
        if (!s.isEmpty()){
            dataBean = new Gson().fromJson(s, DataBean.class);
            adapter.addall(dataBean.data);
        }
    }


    public static void startMe(Context context, String url){
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    private void initView() {
        VideoView videoView = findViewById(R.id.video);
        String url = getIntent().getStringExtra("url");
        extracted(videoView, url);

        RecyclerView recyclerView = findViewById(R.id.recycle_video);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置分隔线
        recyclerView.addItemDecoration(new SpaceItemDecoration(this));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new WebAadpter(this);
        recyclerView.setAdapter(adapter);

        adapter.setClick(new WebAadpter.Itemclick() {
            @Override
            public void click(String url) {
                extracted(videoView,url);
                recyclerView.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.button_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gone = !gone;
                if (gone){
                    recyclerView.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        if (url.contains("m3u8")){
            findViewById(R.id.button_video).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.button_video).setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }



        //创建操作栏
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
    }

    private void extracted(VideoView videoView, String url) {
        Uri uri = Uri.parse(url);
        videoView.setVideoURI(uri);
        videoView.start();


    }
}