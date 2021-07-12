/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package com.google.android.gms.internal;

import android.util.Base64;

public final class zzmo {
    public static String zzj(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        return Base64.encodeToString((byte[])byArray, (int)0);
    }

    public static String zzk(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        return Base64.encodeToString((byte[])byArray, (int)10);
    }
}

