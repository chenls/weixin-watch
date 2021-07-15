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
import com.riyuxihe.weixinqingliao.model.Token;

import java.util.HashMap;
import java.util.Map;

public class CookieImageLoader extends ImageLoader {
    /* access modifiers changed from: private */
    public String cookie;

    public CookieImageLoader(RequestQueue queue, ImageLoader.ImageCache imageCache, String cookie2) {
        super(queue, imageCache);
        this.cookie = cookie2;
    }

    /* access modifiers changed from: protected */
    public Request<Bitmap> makeImageRequest(String requestUrl, int maxWidth, int maxHeight, ImageView.ScaleType scaleType, final String cacheKey) {
        return new ImageRequest(requestUrl, new Response.Listener<Bitmap>() {
            public void onResponse(Bitmap response) {
                CookieImageLoader.this.onGetImageSuccess(cacheKey, response);
            }
        }, maxWidth, maxHeight, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                CookieImageLoader.this.onGetImageError(cacheKey, error);
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put(Token.COOKIE, CookieImageLoader.this.cookie);
                return params;
            }
        };
    }
}
