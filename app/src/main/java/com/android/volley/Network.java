/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;

public interface Network {
    public NetworkResponse performRequest(Request<?> var1) throws VolleyError;
}

