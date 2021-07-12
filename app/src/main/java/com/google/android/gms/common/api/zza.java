/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class zza {
    private static final Map<Activity, zza> zzagA = new WeakHashMap<Activity, zza>();
    private static final Object zzqy = new Object();

    public abstract void remove(int var1);
}

