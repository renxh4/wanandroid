package com.example.wanandroid;

import android.app.Application;

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
}
