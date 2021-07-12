/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.Resources
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.internal.zzne;

public final class zzmu {
    @TargetApi(value=20)
    public static boolean zzaw(Context context) {
        return zzne.zzsl() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean zzb(Resources resources) {
        block3: {
            block2: {
                if (resources == null) break block2;
                boolean bl2 = (resources.getConfiguration().screenLayout & 0xF) > 3;
                if (zzne.zzsd() && bl2 || zzmu.zzc(resources)) break block3;
            }
            return false;
        }
        return true;
    }

    @TargetApi(value=13)
    private static boolean zzc(Resources resources) {
        boolean bl2 = false;
        resources = resources.getConfiguration();
        boolean bl3 = bl2;
        if (zzne.zzsf()) {
            bl3 = bl2;
            if ((resources.screenLayout & 0xF) <= 3) {
                bl3 = bl2;
                if (resources.smallestScreenWidthDp >= 600) {
                    bl3 = true;
                }
            }
        }
        return bl3;
    }
}

