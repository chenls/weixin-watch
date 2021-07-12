/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.internal.zzpl;
import java.util.concurrent.Callable;

public class zzb {
    private static SharedPreferences zzaBZ = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static SharedPreferences zzw(final Context context) {
        synchronized (SharedPreferences.class) {
            if (zzaBZ != null) return zzaBZ;
            zzaBZ = zzpl.zzb(new Callable<SharedPreferences>(){

                @Override
                public /* synthetic */ Object call() throws Exception {
                    return this.zzvw();
                }

                public SharedPreferences zzvw() {
                    return context.getSharedPreferences("google_sdk_flags", 1);
                }
            });
            return zzaBZ;
        }
    }
}

