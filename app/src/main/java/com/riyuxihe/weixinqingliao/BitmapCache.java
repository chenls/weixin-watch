/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.riyuxihe.weixinqingliao;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

public class BitmapCache
implements ImageLoader.ImageCache {
    private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(this.max){

        @Override
        protected int sizeOf(String string2, Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    };
    private int max = 0xA00000;

    @Override
    public Bitmap getBitmap(String string2) {
        return this.lruCache.get(string2);
    }

    @Override
    public void putBitmap(String string2, Bitmap bitmap) {
        this.lruCache.put(string2, bitmap);
    }
}

