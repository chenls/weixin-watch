/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 *  android.text.TextUtils
 */
package com.android.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.InternalUtils;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public abstract class Request<T>
implements Comparable<Request<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    private static long sCounter;
    private Cache.Entry mCacheEntry;
    private boolean mCanceled;
    private final int mDefaultTrafficStatsTag;
    private Response.ErrorListener mErrorListener;
    private final VolleyLog.MarkerLog mEventLog;
    private String mIdentifier;
    private final int mMethod;
    private String mRedirectUrl;
    private RequestQueue mRequestQueue;
    private boolean mResponseDelivered;
    private RetryPolicy mRetryPolicy;
    private Integer mSequence;
    private boolean mShouldCache;
    private Object mTag;
    private final String mUrl;

    /*
     * Enabled aggressive block sorting
     */
    public Request(int n2, String string2, Response.ErrorListener errorListener) {
        VolleyLog.MarkerLog markerLog = VolleyLog.MarkerLog.ENABLED ? new VolleyLog.MarkerLog() : null;
        this.mEventLog = markerLog;
        this.mShouldCache = true;
        this.mCanceled = false;
        this.mResponseDelivered = false;
        this.mCacheEntry = null;
        this.mMethod = n2;
        this.mUrl = string2;
        this.mIdentifier = Request.createIdentifier(n2, string2);
        this.mErrorListener = errorListener;
        this.setRetryPolicy(new DefaultRetryPolicy());
        this.mDefaultTrafficStatsTag = Request.findDefaultTrafficStatsTag(string2);
    }

    @Deprecated
    public Request(String string2, Response.ErrorListener errorListener) {
        this(-1, string2, errorListener);
    }

    private static String createIdentifier(int n2, String charSequence) {
        charSequence = new StringBuilder().append("Request:").append(n2).append(":").append((String)charSequence).append(":").append(System.currentTimeMillis()).append(":");
        long l2 = sCounter;
        sCounter = 1L + l2;
        return InternalUtils.sha1Hash(((StringBuilder)charSequence).append(l2).toString());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private byte[] encodeParameters(Map<String, String> object, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            object = object.entrySet().iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                stringBuilder.append(URLEncoder.encode((String)entry.getKey(), string2));
                stringBuilder.append('=');
                stringBuilder.append(URLEncoder.encode((String)entry.getValue(), string2));
                stringBuilder.append('&');
            }
            return stringBuilder.toString().getBytes(string2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("Encoding not supported: " + string2, unsupportedEncodingException);
        }
    }

    private static int findDefaultTrafficStatsTag(String string2) {
        if (!TextUtils.isEmpty((CharSequence)string2) && (string2 = Uri.parse((String)string2)) != null && (string2 = string2.getHost()) != null) {
            return string2.hashCode();
        }
        return 0;
    }

    public void addMarker(String string2) {
        if (VolleyLog.MarkerLog.ENABLED) {
            this.mEventLog.add(string2, Thread.currentThread().getId());
        }
    }

    public void cancel() {
        this.mCanceled = true;
    }

    @Override
    public int compareTo(Request<T> request) {
        Priority priority;
        Priority priority2 = this.getPriority();
        if (priority2 == (priority = request.getPriority())) {
            return this.mSequence - request.mSequence;
        }
        return priority.ordinal() - priority2.ordinal();
    }

    public void deliverError(VolleyError volleyError) {
        if (this.mErrorListener != null) {
            this.mErrorListener.onErrorResponse(volleyError);
        }
    }

    protected abstract void deliverResponse(T var1);

    void finish(final String string2) {
        long l2;
        block5: {
            block4: {
                if (this.mRequestQueue != null) {
                    this.mRequestQueue.finish(this);
                    this.onFinish();
                }
                if (!VolleyLog.MarkerLog.ENABLED) break block4;
                l2 = Thread.currentThread().getId();
                if (Looper.myLooper() == Looper.getMainLooper()) break block5;
                new Handler(Looper.getMainLooper()).post(new Runnable(){

                    @Override
                    public void run() {
                        Request.this.mEventLog.add(string2, l2);
                        Request.this.mEventLog.finish(this.toString());
                    }
                });
            }
            return;
        }
        this.mEventLog.add(string2, l2);
        this.mEventLog.finish(this.toString());
    }

    public byte[] getBody() throws AuthFailureError {
        Map<String, String> map = this.getParams();
        if (map != null && map.size() > 0) {
            return this.encodeParameters(map, this.getParamsEncoding());
        }
        return null;
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + this.getParamsEncoding();
    }

    public Cache.Entry getCacheEntry() {
        return this.mCacheEntry;
    }

    public String getCacheKey() {
        return this.mMethod + ":" + this.mUrl;
    }

    public Response.ErrorListener getErrorListener() {
        return this.mErrorListener;
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return Collections.emptyMap();
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public int getMethod() {
        return this.mMethod;
    }

    public String getOriginUrl() {
        return this.mUrl;
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return null;
    }

    protected String getParamsEncoding() {
        return "UTF-8";
    }

    @Deprecated
    public byte[] getPostBody() throws AuthFailureError {
        Map<String, String> map = this.getPostParams();
        if (map != null && map.size() > 0) {
            return this.encodeParameters(map, this.getPostParamsEncoding());
        }
        return null;
    }

    @Deprecated
    public String getPostBodyContentType() {
        return this.getBodyContentType();
    }

    @Deprecated
    protected Map<String, String> getPostParams() throws AuthFailureError {
        return this.getParams();
    }

    @Deprecated
    protected String getPostParamsEncoding() {
        return this.getParamsEncoding();
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public RetryPolicy getRetryPolicy() {
        return this.mRetryPolicy;
    }

    public final int getSequence() {
        if (this.mSequence == null) {
            throw new IllegalStateException("getSequence called before setSequence");
        }
        return this.mSequence;
    }

    public Object getTag() {
        return this.mTag;
    }

    public final int getTimeoutMs() {
        return this.mRetryPolicy.getCurrentTimeout();
    }

    public int getTrafficStatsTag() {
        return this.mDefaultTrafficStatsTag;
    }

    public String getUrl() {
        if (this.mRedirectUrl != null) {
            return this.mRedirectUrl;
        }
        return this.mUrl;
    }

    public boolean hasHadResponseDelivered() {
        return this.mResponseDelivered;
    }

    public boolean isCanceled() {
        return this.mCanceled;
    }

    public void markDelivered() {
        this.mResponseDelivered = true;
    }

    protected void onFinish() {
        this.mErrorListener = null;
    }

    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    protected abstract Response<T> parseNetworkResponse(NetworkResponse var1);

    public Request<?> setCacheEntry(Cache.Entry entry) {
        this.mCacheEntry = entry;
        return this;
    }

    public void setRedirectUrl(String string2) {
        this.mRedirectUrl = string2;
    }

    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        this.mRequestQueue = requestQueue;
        return this;
    }

    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        this.mRetryPolicy = retryPolicy;
        return this;
    }

    public final Request<?> setSequence(int n2) {
        this.mSequence = n2;
        return this;
    }

    public final Request<?> setShouldCache(boolean bl2) {
        this.mShouldCache = bl2;
        return this;
    }

    public Request<?> setTag(Object object) {
        this.mTag = object;
        return this;
    }

    public final boolean shouldCache() {
        return this.mShouldCache;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        String string3 = "0x" + Integer.toHexString(this.getTrafficStatsTag());
        StringBuilder stringBuilder = new StringBuilder();
        if (this.mCanceled) {
            string2 = "[X] ";
            return stringBuilder.append(string2).append(this.getUrl()).append(" ").append(string3).append(" ").append((Object)this.getPriority()).append(" ").append(this.mSequence).toString();
        }
        string2 = "[ ] ";
        return stringBuilder.append(string2).append(this.getUrl()).append(" ").append(string3).append(" ").append((Object)this.getPriority()).append(" ").append(this.mSequence).toString();
    }

    public static interface Method {
        public static final int DELETE = 3;
        public static final int DEPRECATED_GET_OR_POST = -1;
        public static final int GET = 0;
        public static final int HEAD = 4;
        public static final int OPTIONS = 5;
        public static final int PATCH = 7;
        public static final int POST = 1;
        public static final int PUT = 2;
        public static final int TRACE = 6;
    }

    public static enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE;

    }
}

