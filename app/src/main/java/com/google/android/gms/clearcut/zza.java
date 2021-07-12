/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.clearcut;

import android.content.Context;

public class zza {
    private static int zzaeO = -1;
    public static final zza zzaeP = new zza();

    protected zza() {
    }

    public int zzah(Context context) {
        if (zzaeO < 0) {
            zzaeO = context.getSharedPreferences("bootCount", 0).getInt("bootCount", 1);
        }
        return zzaeO;
    }
}

