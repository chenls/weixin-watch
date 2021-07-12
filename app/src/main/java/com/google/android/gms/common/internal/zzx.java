/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.text.TextUtils
 */
package com.google.android.gms.common.internal;

import android.os.Looper;
import android.text.TextUtils;

public final class zzx {
    public static int zza(int n2, Object object) {
        if (n2 == 0) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return n2;
    }

    public static long zza(long l2, Object object) {
        if (l2 == 0L) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return l2;
    }

    public static void zza(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalStateException(String.valueOf(object));
        }
    }

    public static void zza(boolean bl2, String string2, Object ... objectArray) {
        if (!bl2) {
            throw new IllegalStateException(String.format(string2, objectArray));
        }
    }

    public static void zzab(boolean bl2) {
        if (!bl2) {
            throw new IllegalStateException();
        }
    }

    public static void zzac(boolean bl2) {
        if (!bl2) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T zzb(T t2, Object object) {
        if (t2 == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t2;
    }

    public static void zzb(boolean bl2, Object object) {
        if (!bl2) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
    }

    public static void zzb(boolean bl2, String string2, Object ... objectArray) {
        if (!bl2) {
            throw new IllegalArgumentException(String.format(string2, objectArray));
        }
    }

    public static int zzbV(int n2) {
        if (n2 == 0) {
            throw new IllegalArgumentException("Given Integer is zero");
        }
        return n2;
    }

    public static void zzcD(String string2) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(string2);
        }
    }

    public static void zzcE(String string2) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException(string2);
        }
    }

    public static String zzcM(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("Given String is empty or null");
        }
        return string2;
    }

    public static String zzh(String string2, Object object) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return string2;
    }

    public static <T> T zzz(T t2) {
        if (t2 == null) {
            throw new NullPointerException("null reference");
        }
        return t2;
    }
}

