package com.example.wanandroid.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.Utils;
import com.example.wanandroid.activity.VideoActivity;
import com.example.wanandroid.activity.Webview2Activity;
import com.example.wanandroid.adapter.ArticleAdapter;
import com.example.wanandroid.adapter.CustomLinearLayoutManager;
import com.example.wanandroid.adapter.QuestionAdapter;
import com.example.wanandroid.adapter.SpaceItemDecoration;
import com.example.wanandroid.adapter.TimeAdapter;
import com.example.wanandroid.bean.TimeBean;
import com.example.wanandroid.utils.SharePerferenceUtils;
import com.example.wanandroid.utils.StringUtils;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SettingFragment extends Fragment {

    private final String mText;
    private RecyclerView recyclerView;
    private int a = 2031645;
    private boolean show = false;
    private DatePicker dp_test;
    private TextView shiji;
    private Button yijing;
    private RecyclerView recyclerView1;
    private TimeAdapter articleAdapter;
    private EditText editText;
    private Handler handler = new Handler(Looper.getMainLooper());


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
        initData();
    }

    private void initData() {
        refresh();
    }

    private void refresh() {
        String edit = SharePerferenceUtils.getString(requireContext(), "edit", "");
        if (StringUtils.isNotEmpty(edit)){
            editText.setText(edit);
        }
        String mubiao = SharePerferenceUtils.getString(requireContext(), "mubiao", "");
        if (StringUtils.isNotEmpty(mubiao)){
            TimeBean timeBean = new Gson().fromJson(mubiao, TimeBean.class);
            ArrayList<String> data = timeBean.data;
            articleAdapter.setData(data);
            if (data.size()>0){
                String strDateFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                String format = sdf.format(new Date(Long.valueOf(timeBean.data.get(0))));
                shiji.setText("上次完成时间"+format);

                long during = System.currentTimeMillis() - Long.valueOf(data.get(0)) ;
                int day = (int) (during/(1000*60*60*24));
                yijing.setText("距上次"+day+"天时间");
            }
        }
    }

    private void initView(View view) {
        view.findViewById(R.id.setting_chanding).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

        editText = view.findViewById(R.id.setting_jianchi_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE) {
                    String keyWord = editText.getText().toString().trim();
                    SharePerferenceUtils.putString(requireContext(),"edit",keyWord);
                    hideInputMethod();
                    return true;
                }
                return false;
            }
        });
        recyclerView = view.findViewById(R.id.recycle);
        //设置布局管理器
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        articleAdapter = new TimeAdapter(requireContext());
        //设置Adapter
        recyclerView.setAdapter(articleAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new SpaceItemDecoration(getContext()));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        yijing = view.findViewById(R.id.setting_yijing);
        shiji = view.findViewById(R.id.setting_wancheng_text);
        view.findViewById(R.id.setting_wancheng).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show){
                    dp_test.setVisibility(View.GONE);
                }else {
                    dp_test.setVisibility(View.VISIBLE);
                }
                show = !show;
            }
        });
        dp_test = (DatePicker) view.findViewById(R.id.datepicker);
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int monthOfYear=calendar.get(Calendar.MONTH);
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        dp_test.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Utils.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(requireContext(),"您选择的日期是："+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日!",Toast.LENGTH_SHORT).show();
                        String time  = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        shiji.setText(time);
                        String strDateFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                        try {
                            Date parse = sdf.parse(time);
                            long time1 = parse.getTime();
                            toSp(String.valueOf(time1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dp_test.setVisibility(View.GONE);
                        show = false;
                        refresh();

                    }
                });

            }
        });
    }

    public void hideInputMethod() {
        ((InputMethodManager)requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void toSp(String time){
        String mubiao = SharePerferenceUtils.getString(requireContext(), "mubiao", "");
        if (StringUtils.isNotEmpty(mubiao)){
            TimeBean timeBean = new Gson().fromJson(mubiao, TimeBean.class);
            ArrayList<String> data = timeBean.data;
            data.add(0,time);
            String s = new Gson().toJson(timeBean);
            SharePerferenceUtils.putString(requireContext(),"mubiao",s);
        }else {
            TimeBean timeBean = new TimeBean();
            ArrayList<String> strings = new ArrayList<>();
            strings.add(time);
            timeBean.data  = strings;
            String s = new Gson().toJson(timeBean);
            SharePerferenceUtils.putString(requireContext(),"mubiao",s);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    open();
                }
            },1000);
        }
    }

    private void open() {
        String package_name="com.yunlian.meditationmode";
        String activity_path = "com.yunlian.meditationmode.act.MainActDing";
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//可选
        ComponentName comp = new ComponentName(package_name,activity_path);
        intent.setComponent(comp);
        startActivity(intent);
    }
}
