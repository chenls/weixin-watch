/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

public class RedirectError
extends VolleyError {
    public RedirectError() {
    }

    public RedirectError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public RedirectError(Throwable throwable) {
        super(throwable);
    }
}

