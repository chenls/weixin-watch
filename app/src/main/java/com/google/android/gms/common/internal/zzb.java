/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Looper;
import android.util.Log;

public final class zzb {
    public static void zza(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static void zzab(boolean bl2) {
        if (!bl2) {
            throw new IllegalStateException();
        }
    }

    public static void zzcD(String string2) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            Log.e((String)"Asserts", (String)("checkMainThread: current thread " + Thread.currentThread() + " IS NOT the main thread " + Looper.getMainLooper().getThread() + "!"));
            throw new IllegalStateException(string2);
        }
    }

    public static void zzcE(String string2) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Log.e((String)"Asserts", (String)("checkNotMainThread: current thread " + Thread.currentThread() + " IS the main thread " + Looper.getMainLooper().getThread() + "!"));
            throw new IllegalStateException(string2);
        }
    }

    public static void zzv(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("null reference");
        }
    }
}

