/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class NetworkResponse
implements Serializable {
    private static final long serialVersionUID = -20150728102000L;
    public final byte[] data;
    public final Map<String, String> headers;
    public final long networkTimeMs;
    public final boolean notModified;
    public final int statusCode;

    public NetworkResponse(int n2, byte[] byArray, Map<String, String> map, boolean bl2) {
        this(n2, byArray, map, bl2, 0L);
    }

    public NetworkResponse(int n2, byte[] byArray, Map<String, String> map, boolean bl2, long l2) {
        this.statusCode = n2;
        this.data = byArray;
        this.headers = map;
        this.notModified = bl2;
        this.networkTimeMs = l2;
    }

    public NetworkResponse(byte[] byArray) {
        this(200, byArray, Collections.emptyMap(), false, 0L);
    }

    public NetworkResponse(byte[] byArray, Map<String, String> map) {
        this(200, byArray, map, false, 0L);
    }
}

