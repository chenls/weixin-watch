/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 */
package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import com.google.android.gms.common.internal.zzm;

public abstract class zzl {
    private static final Object zzalX = new Object();
    private static zzl zzalY;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzl zzau(Context context) {
        Object object = zzalX;
        synchronized (object) {
            if (zzalY == null) {
                zzalY = new zzm(context.getApplicationContext());
            }
            return zzalY;
        }
    }

    public abstract boolean zza(ComponentName var1, ServiceConnection var2, String var3);

    public abstract boolean zza(String var1, ServiceConnection var2, String var3);

    public abstract void zzb(ComponentName var1, ServiceConnection var2, String var3);

    public abstract void zzb(String var1, ServiceConnection var2, String var3);
}

