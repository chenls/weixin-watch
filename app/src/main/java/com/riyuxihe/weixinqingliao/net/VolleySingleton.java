/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.riyuxihe.weixinqingliao.net;

import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.riyuxihe.weixinqingliao.BitmapCache;
import com.riyuxihe.weixinqingliao.MyApplication;
import com.riyuxihe.weixinqingliao.net.CookieImageLoader;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private String cookie;
    private ImageLoader imageLoader;
    private RequestQueue mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

    private VolleySingleton() {
    }

    public static VolleySingleton getInstance() {
        if (mInstance == null) {
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public ImageLoader getImageLoader(String string2) {
        if (string2 == null) {
            Log.e((String)"VolleySingleton", (String)"getImageLoader:cookie should not be null");
        }
        if (this.imageLoader == null || !string2.equals(this.cookie)) {
            this.cookie = string2;
            this.imageLoader = new CookieImageLoader(this.mRequestQueue, new BitmapCache(), string2);
        }
        return this.imageLoader;
    }

    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }
}

