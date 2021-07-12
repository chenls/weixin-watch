/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.flags.impl.zza;
import com.google.android.gms.flags.impl.zzb;
import com.google.android.gms.internal.zzpk;

public class FlagProviderImpl
extends zzpk.zza {
    private boolean zzqA = false;
    private SharedPreferences zzvx;

    @Override
    public boolean getBooleanFlagValue(String string2, boolean bl2, int n2) {
        return zza.zza.zza(this.zzvx, string2, bl2);
    }

    @Override
    public int getIntFlagValue(String string2, int n2, int n3) {
        return zza.zzb.zza(this.zzvx, string2, n2);
    }

    @Override
    public long getLongFlagValue(String string2, long l2, int n2) {
        return zza.zzc.zza(this.zzvx, string2, l2);
    }

    @Override
    public String getStringFlagValue(String string2, String string3, int n2) {
        return zza.zzd.zza(this.zzvx, string2, string3);
    }

    @Override
    public void init(zzd zzd2) {
        zzd2 = (Context)zze.zzp(zzd2);
        if (this.zzqA) {
            return;
        }
        try {
            this.zzvx = zzb.zzw(zzd2.createPackageContext("com.google.android.gms", 0));
            this.zzqA = true;
            return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return;
        }
    }
}

