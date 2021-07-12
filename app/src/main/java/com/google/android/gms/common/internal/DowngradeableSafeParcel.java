/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public abstract class DowngradeableSafeParcel
implements SafeParcelable {
    private static final Object zzalh = new Object();
    private static ClassLoader zzali = null;
    private static Integer zzalj = null;
    private boolean zzalk = false;

    private static boolean zza(Class<?> clazz) {
        try {
            boolean bl2 = "SAFE_PARCELABLE_NULL_STRING".equals(clazz.getField("NULL").get(null));
            return bl2;
        }
        catch (IllegalAccessException illegalAccessException) {
            return false;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return false;
        }
    }

    protected static boolean zzcF(String string2) {
        ClassLoader classLoader = DowngradeableSafeParcel.zzqA();
        if (classLoader == null) {
            return true;
        }
        try {
            boolean bl2 = DowngradeableSafeParcel.zza(classLoader.loadClass(string2));
            return bl2;
        }
        catch (Exception exception) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static ClassLoader zzqA() {
        Object object = zzalh;
        synchronized (object) {
            return zzali;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected static Integer zzqB() {
        Object object = zzalh;
        synchronized (object) {
            return zzalj;
        }
    }

    protected boolean zzqC() {
        return this.zzalk;
    }
}

