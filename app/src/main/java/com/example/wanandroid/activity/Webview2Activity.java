package com.example.wanandroid.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.Utils;
import com.example.wanandroid.adapter.CustomLinearLayoutManager;
import com.example.wanandroid.adapter.SpaceItemDecoration;
import com.example.wanandroid.adapter.WebAadpter;
import com.example.wanandroid.base.DataBean;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Webview2Activity extends AppCompatActivity {

    public final String TAG = "mmmWebviewActivity";
    public static final String URL = "url";
    private WebView webView;
    private ProgressBar progressBar;
    private WebAadpter adapter;
    private boolean gone;
    private DataBean dataBean1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview2);
        initView();
        initData();

    }


    public static void start(Context context, String url) {
        Intent intent = new Intent(context, Webview2Activity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    private void initView() {
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progress);
        RecyclerView recyclerView = findViewById(R.id.recycle_web);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        //?????????????????????
        recyclerView.setLayoutManager(layoutManager);
        //???????????????
        recyclerView.addItemDecoration(new SpaceItemDecoration(this));
        //????????????????????????????????????
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new WebAadpter(this);
        recyclerView.setAdapter(adapter);

        adapter.setClick(new WebAadpter.Itemclick() {
            @Override
            public void click(String url) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // ?????????????????????ClipData
                ClipData mClipData = ClipData.newPlainText("url", url);
                // ???ClipData?????????????????????????????????
                clipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(Webview2Activity.this, "????????????!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.web_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gone = !gone;
                if (gone) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.web_button_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = new Gson().toJson(dataBean1);
                Utils.save(Webview2Activity.this,s);
                finish();
            }
        });
        initWebview();
    }

    private void initWebview() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true); // ??????javascript
        settings.setDomStorageEnabled(true); // ??????HTML5????????????????????????
        settings.setBuiltInZoomControls(false); // ??????????????????
        //??????http???https???????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        } else {
            settings.setMixedContentMode(WebSettings.LOAD_NORMAL);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // ??????javascript??????
            try {
                Method method = Class.forName("android.webkit.WebView").
                        getMethod("setWebContentsDebuggingEnabled", Boolean.TYPE);
                if (method != null) {
                    method.setAccessible(true);
                    method.invoke(null, true);
                }
            } catch (Exception e) {
                // do nothing
            }
        }

        webView.setFocusable(true); // ??????????????????
        webView.setDrawingCacheEnabled(true); // ??????????????????
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY); // ??????????????????
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) { // ???????????????????????????
                progressBar.setProgress(progress);
                progressBar.setVisibility((progress > 0 && progress < 100) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false); // ????????????????????????????????????
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "????????????" + url);
                Utils.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
//                        adapter.add(url);
                    }
                });
                if (url.startsWith("jianshu")) {
                    return true;
                }
                if (url.startsWith("http://") || url.startsWith("https://")) { // 4.0??????????????????
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d(TAG, request.getUrl().toString());
                if (request.getUrl().toString().endsWith(".m3u8")){
                    Utils.executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.add(request.getUrl().toString());
                            dataBean1.data.add(request.getUrl().toString());
                        }
                    });
                }
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error)
                switch (error.getPrimaryError()) {
                    case SslError.SSL_INVALID: // ?????????????????????bug
                    case SslError.SSL_UNTRUSTED: // ???????????????
                        handler.proceed();
                    default:
                        handler.cancel();
                }
            }
        });
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra(URL);
        webView.loadUrl(url);

        String s = Utils.get(this);
        if (!s.isEmpty()){
            dataBean1 = new Gson().fromJson(s, DataBean.class);
        }else {
            dataBean1 = new DataBean();
            dataBean1.data = new ArrayList<String>();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String s = new Gson().toJson(dataBean1);
        Utils.save(this,s);
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????
     *
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();//??????????????????
            return true;
        }
        return super.onKeyDown(keyCode, event);//????????????????????????
    }
}