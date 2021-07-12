/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley;

import com.android.volley.VolleyError;

public interface RetryPolicy {
    public int getCurrentRetryCount();

    public int getCurrentTimeout();

    public void retry(VolleyError var1) throws VolleyError;
}

