/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Process
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.internal.zzlz;

public class zzmp {
    public static boolean zzk(Context context, String string2) {
        boolean bl2 = false;
        context = context.getPackageManager();
        try {
            int n2 = context.getApplicationInfo((String)string2, (int)0).flags;
            if ((n2 & 0x200000) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    public static boolean zzkr() {
        return zzd.zzakE && zzlz.isInitialized() && zzlz.zzpW() == Process.myUid();
    }
}

