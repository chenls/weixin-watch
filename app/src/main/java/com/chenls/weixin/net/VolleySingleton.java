package com.chenls.weixin.net;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.chenls.weixin.BitmapCache;
import com.chenls.weixin.MyApplication;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private final RequestQueue mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    private String cookie;
    private ImageLoader imageLoader;

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
