/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import java.io.UnsupportedEncodingException;

public class StringRequest
extends Request<String> {
    private Response.Listener<String> mListener;

    public StringRequest(int n2, String string2, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(n2, string2, errorListener);
        this.mListener = listener;
    }

    public StringRequest(String string2, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(0, string2, listener, errorListener);
    }

    @Override
    protected void deliverResponse(String string2) {
        if (this.mListener != null) {
            this.mListener.onResponse(string2);
        }
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        this.mListener = null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {
        String string2;
        try {
            string2 = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            string2 = new String(networkResponse.data);
            return Response.success(string2, HttpHeaderParser.parseCacheHeaders(networkResponse));
        }
        return Response.success(string2, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }
}

