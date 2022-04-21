package com.example.wanandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wanandroid.R;

public class SettingFragment extends Fragment {

    private final String mText;

    private SettingFragment(String text){
        this.mText = text;
    }

    public static SettingFragment newInstance(String text){
        return new SettingFragment(text);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragmet,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView textView = view.findViewById(R.id.home_fragment_text);
        textView.setText(mText);
    }
}
