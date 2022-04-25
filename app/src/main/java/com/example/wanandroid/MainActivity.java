package com.example.wanandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanandroid.fragment.HomeFragment;
import com.example.wanandroid.fragment.QuestionFragment;
import com.example.wanandroid.fragment.SettingFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    public ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] tab_titles;
    private int[] tab_imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        fragments.add(HomeFragment.newInstance("首页"));
        fragments.add(QuestionFragment.newInstance());
        fragments.add(SettingFragment.newInstance("公众号"));
        fragments.add(SettingFragment.newInstance("体系"));
        fragments.add(SettingFragment.newInstance("项目"));
        tab_titles = new String[]{"首页", "问答", "公众号", "体系", "项目"};
        tab_imgs = new int[]{R.drawable.ic_home_black_24dp, R.drawable.ic_square_black_24dp,
                R.drawable.ic_wechat_black_24dp, R.drawable.ic_apps_black_24dp, R.drawable.ic_project_black_24dp};
        setTabs(tab_titles, tab_imgs);
        //设置viewpager的adapter
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        //TabLayout与ViewPager的绑定
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);
    }

    /**
     * 设置添加Tab
     *
     * @param tab_titles tab条目名字
     * @param tab_imgs   tab上条目上的图片
     */
    private void setTabs(String[] tab_titles, int[] tab_imgs) {
        for (int i = 0; i < tab_titles.length; i++) {
            //获取TabLayout的tab
            TabLayout.Tab tab = mTabLayout.newTab();
            //初始化条目布局view
            View view = getLayoutInflater().inflate(R.layout.tab_item, null);
            tab.setCustomView(view);
            //tab的文字
            TextView tvTitle = view.findViewById(R.id.tv_des);
            tvTitle.setText(tab_titles[i]);
            //tab的图片
            ImageView imgTab = view.findViewById(R.id.iv_top);
            imgTab.setImageResource(tab_imgs[i]);
            if (i == 0) {
                //设置第一个默认选中
                mTabLayout.addTab(tab, true);
            } else {
                mTabLayout.addTab(tab, false);
            }
        }
    }
}