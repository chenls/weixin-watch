/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.os.Build;

public final class zzne {
    @Deprecated
    public static boolean isAtLeastL() {
        return zzne.zzsm();
    }

    private static boolean zzcp(int n2) {
        return Build.VERSION.SDK_INT >= n2;
    }

    public static boolean zzsd() {
        return zzne.zzcp(11);
    }

    public static boolean zzse() {
        return zzne.zzcp(12);
    }

    public static boolean zzsf() {
        return zzne.zzcp(13);
    }

    public static boolean zzsg() {
        return zzne.zzcp(14);
    }

    public static boolean zzsh() {
        return zzne.zzcp(16);
    }

    public static boolean zzsi() {
        return zzne.zzcp(17);
    }

    public static boolean zzsj() {
        return zzne.zzcp(18);
    }

    public static boolean zzsk() {
        return zzne.zzcp(19);
    }

    public static boolean zzsl() {
        return zzne.zzcp(20);
    }

    public static boolean zzsm() {
        return zzne.zzcp(21);
    }

    public static boolean zzsn() {
        return zzne.zzcp(23);
    }
}

