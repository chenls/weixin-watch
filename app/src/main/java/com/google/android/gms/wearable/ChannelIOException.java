/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable;

import java.io.IOException;

public class ChannelIOException
extends IOException {
    private final int zzbqW;
    private final int zzbqX;

    public ChannelIOException(String string2, int n2, int n3) {
        super(string2);
        this.zzbqW = n2;
        this.zzbqX = n3;
    }

    public int getAppSpecificErrorCode() {
        return this.zzbqX;
    }

    public int getCloseReason() {
        return this.zzbqW;
    }
}

