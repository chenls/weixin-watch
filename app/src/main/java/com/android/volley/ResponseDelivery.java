/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public interface ResponseDelivery {
    public void postError(Request<?> var1, VolleyError var2);

    public void postResponse(Request<?> var1, Response<?> var2);

    public void postResponse(Request<?> var1, Response<?> var2, Runnable var3);
}

