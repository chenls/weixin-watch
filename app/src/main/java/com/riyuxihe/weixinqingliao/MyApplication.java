package com.riyuxihe.weixinqingliao;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context mAppContext;
    private static MyApplication mInstance;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext2) {
        mAppContext = mAppContext2;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setAppContext(getApplicationContext());
    }
}
