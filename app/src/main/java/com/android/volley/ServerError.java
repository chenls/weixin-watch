/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

public class ServerError
extends VolleyError {
    public ServerError() {
    }

    public ServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}

