package com.example.wanandroid;

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
}
