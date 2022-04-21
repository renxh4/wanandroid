package com.example.wanandroid.net;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkhttpManager {
    public static OkhttpManager INSTANCE = new OkhttpManager();
    private final OkHttpClient mOkHttpClient;
    public String COOKIE_SPLIT = ";";
    public String COOKIE_KEY = "cookie";

    private OkhttpManager() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();
    }


    public void post(String url, Map<String, String> params, Map<String, String> header,
                     Map<String, String> cookie, CallBack callBack) {
        if (header == null) {
            header = new HashMap<>();
        }
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (value != null) {
                    formBody.add(key, value);
                }
            }
        }
        FormBody requestBody = formBody.build();
        initHeader(cookie, header);
        Headers.Builder builder = new Headers.Builder();
        for (String key : header.keySet()) {
            String value = header.get(key);
            if (value != null) {
                builder.add(key, value);
            }
        }
        Headers headers = builder.build();
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();
        //4.创建一个call对象,参数就是Request请求对象
        Call call = mOkHttpClient.newCall(request);
        //5.请求加入调度,重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack!=null){
                    callBack.fail(e.getMessage()+"/");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack!=null){
                    callBack.success(Objects.requireNonNull(response.body()).string());
                }
            }
        });

    }

    private void initHeader(Map<String, String> cookies, Map<String, String> header) {
        String cookie = "";
        if (null != cookies) {
            for (String key : cookies.keySet()) {
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(cookies.get(key))) {
                    continue;
                }
                if (!TextUtils.isEmpty(cookie)) {
                    cookie += COOKIE_SPLIT;
                }
                cookie += key + "=" + cookies.get(key);
            }
        }
        if (!TextUtils.isEmpty(cookie)) {
            header.put(COOKIE_KEY, cookie);
        }
    }

    public void get(String url, CallBack callBack) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack!=null){
                    callBack.fail(e.getMessage()+"/");
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack!=null){
                    callBack.success(Objects.requireNonNull(response.body()).string());
                }
            }
        });
    }


    public interface CallBack {
        void success(String json);

        void fail(String msg);
    }


}
