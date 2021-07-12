/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.riyuxihe.weixinqingliao.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class CookieRequest
extends JsonObjectRequest {
    private Map<String, String> mHeaders = new HashMap<String, String>();

    public CookieRequest(int n2, String string2, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(n2, string2, listener, errorListener);
    }

    public CookieRequest(int n2, String string2, String string3, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(n2, string2, string3, listener, errorListener);
    }

    public CookieRequest(int n2, String string2, JSONObject jSONObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(n2, string2, jSONObject, listener, errorListener);
    }

    public CookieRequest(String string2, JSONObject jSONObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(string2, jSONObject, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        this.mHeaders.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/51.0.2704.79 Chrome/51.0.2704.79 Safari/537.36");
        this.mHeaders.put("Referer", "https://wx.qq.com/");
        return this.mHeaders;
    }

    public void setCookie(String string2) {
        this.mHeaders.put("Cookie", string2);
    }
}

