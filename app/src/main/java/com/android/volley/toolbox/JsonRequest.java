/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import java.io.UnsupportedEncodingException;

public abstract class JsonRequest<T>
extends Request<T> {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", "utf-8");
    private Response.Listener<T> mListener;
    private final String mRequestBody;

    public JsonRequest(int n2, String string2, String string3, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(n2, string2, errorListener);
        this.mListener = listener;
        this.mRequestBody = string3;
    }

    public JsonRequest(String string2, String string3, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(-1, string2, string3, listener, errorListener);
    }

    @Override
    protected void deliverResponse(T t2) {
        if (this.mListener != null) {
            this.mListener.onResponse(t2);
        }
    }

    @Override
    public byte[] getBody() {
        block3: {
            try {
                if (this.mRequestBody != null) break block3;
                return null;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", this.mRequestBody, PROTOCOL_CHARSET);
                return null;
            }
        }
        byte[] byArray = this.mRequestBody.getBytes(PROTOCOL_CHARSET);
        return byArray;
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getPostBody() {
        return this.getBody();
    }

    @Override
    public String getPostBodyContentType() {
        return this.getBodyContentType();
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        this.mListener = null;
    }

    @Override
    protected abstract Response<T> parseNetworkResponse(NetworkResponse var1);
}

