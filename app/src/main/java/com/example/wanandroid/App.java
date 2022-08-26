package com.example.wanandroid;

import android.app.Application;

import com.renxh.mock.MockSdk;

public class App extends Application {
    private static App sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        MockSdk.INSTANCE.init(this);
    }

    public static Application getApplication() {
        return sApplication;
    }
}
