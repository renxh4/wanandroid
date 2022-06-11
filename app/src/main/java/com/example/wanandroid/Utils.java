package com.example.wanandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

public class Utils {
    static Handler sHandler = new Handler(Looper.getMainLooper());

    public static  void executeOnMainThread(Runnable r) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            r.run();
        } else {
            sHandler.post(r);
        }
    }

    public static void save(Context context,String url){
        SharedPreferences sharedPreferences=context.getSharedPreferences("sp",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("url",url);
        editor.commit();

    }

    public static String get(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("sp",context.MODE_PRIVATE);
        String url = sharedPreferences.getString("url", "");
        return url;
    }
}
