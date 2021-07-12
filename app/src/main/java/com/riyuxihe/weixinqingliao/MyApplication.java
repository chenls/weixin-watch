/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 */
package com.riyuxihe.weixinqingliao;

import android.app.Application;
import android.content.Context;

public class MyApplication
extends Application {
    private static Context mAppContext;
    private static MyApplication mInstance;

    public static Context getAppContext() {
        return mAppContext;
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.setAppContext(this.getApplicationContext());
    }

    public void setAppContext(Context context) {
        mAppContext = context;
    }
}

