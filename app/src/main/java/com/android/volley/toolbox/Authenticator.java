/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;

public interface Authenticator {
    public String getAuthToken() throws AuthFailureError;

    public void invalidateAuthToken(String var1);
}

