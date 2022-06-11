package com.example.wanandroid.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.activity.VideoActivity;
import com.example.wanandroid.activity.Webview2Activity;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.CustomLinearLayoutManager;
import com.example.wanandroid.adapter.QuestionAdapter;
import com.example.wanandroid.adapter.SpaceItemDecoration;

public class SettingFragment extends Fragment {

    private final String mText;
    private RecyclerView recyclerView;
    private int a = 2031645;

    public SettingFragment(String text){
        this.mText = text;
    }

    public static SettingFragment newInstance(String text){
        return new SettingFragment(text);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_fragmet,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {
        EditText editText = view.findViewById(R.id.edit);
        Button button = view.findViewById(R.id.search);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://hj3f23.com/
                String trim = editText.getText().toString().trim();
                if (trim.equals("adhu")){
                    trim = "https://hj3f23.com/";
                }
                Webview2Activity.start(getContext(),trim);
            }
        });

        view.findViewById(R.id.search_mp4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://test.aofahairextension.com/hjstore/video/20220605/e0812678bb965b3cd41040446e76a248/2031645_i.m3u8
                String url = "https://test.aofahairextension.com/hjstore/video/20220605/e0812678bb965b3cd41040446e76a248/%s_i.m3u8";
                Log.d("mmm a == ", a+"/");
                String format = String.format(url, a);
                String trim = editText.getText().toString().trim();
                if (trim.equals("adhu")){
                    trim = format;
                }
                editText.setText(trim);
                VideoActivity.startMe(getContext(),trim);
            }
        });

        view.findViewById(R.id.search_mp41).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

}
