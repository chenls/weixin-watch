package com.riyuxihe.weixinqingliao.net;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.riyuxihe.weixinqingliao.BitmapCache;
import com.riyuxihe.weixinqingliao.MyApplication;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private String cookie;
    private ImageLoader imageLoader;
    private final RequestQueue mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

    private VolleySingleton() {
    }

    public static VolleySingleton getInstance() {
        if (mInstance == null) {
            mInstance = new VolleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(String cookie2) {
        if (cookie2 == null) {
            Log.e("VolleySingleton", "getImageLoader:cookie should not be null");
        }
        if (this.imageLoader == null || !cookie2.equals(this.cookie)) {
            this.cookie = cookie2;
            this.imageLoader = new CookieImageLoader(this.mRequestQueue, new BitmapCache(), cookie2);
        }
        return this.imageLoader;
    }
}
