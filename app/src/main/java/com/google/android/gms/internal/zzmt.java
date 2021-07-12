/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.os.SystemClock;
import com.google.android.gms.internal.zzmq;

public final class zzmt
implements zzmq {
    private static zzmt zzaoa;

    public static zzmq zzsc() {
        synchronized (zzmt.class) {
            if (zzaoa == null) {
                zzaoa = new zzmt();
            }
            zzmt zzmt2 = zzaoa;
            return zzmt2;
        }
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    @Override
    public long nanoTime() {
        return System.nanoTime();
    }
}

