package com.example.wanandroid;

import android.app.Application;
import android.content.Context;

import com.renxh.mock.MockSdk;

public class App extends Application {
    private static App sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    public static Application getApplication() {
        return sApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
