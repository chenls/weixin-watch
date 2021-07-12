/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.widget.ImageView$ScaleType
 */
package com.riyuxihe.weixinqingliao.net;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import java.util.HashMap;
import java.util.Map;

public class CookieImageLoader
extends ImageLoader {
    private String cookie;

    public CookieImageLoader(RequestQueue requestQueue, ImageLoader.ImageCache imageCache, String string2) {
        super(requestQueue, imageCache);
        this.cookie = string2;
    }

    @Override
    protected Request<Bitmap> makeImageRequest(String string2, int n2, int n3, ImageView.ScaleType scaleType, final String string3) {
        return new ImageRequest(string2, new Response.Listener<Bitmap>(){

            @Override
            public void onResponse(Bitmap bitmap) {
                CookieImageLoader.this.onGetImageSuccess(string3, bitmap);
            }
        }, n2, n3, Bitmap.Config.RGB_565, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CookieImageLoader.this.onGetImageError(string3, volleyError);
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("Cookie", CookieImageLoader.this.cookie);
                return hashMap;
            }
        };
    }
}

