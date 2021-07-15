package com.riyuxihe.weixinqingliao;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class BitmapCache implements ImageLoader.ImageCache {
    private final int max = 10485760;
    private final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(this.max) {
        /* access modifiers changed from: protected */
        public int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }
    };

    public Bitmap getBitmap(String url) {
        return this.lruCache.get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        this.lruCache.put(url, bitmap);
    }
}
