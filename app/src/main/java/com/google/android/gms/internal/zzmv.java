/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.PowerManager
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.google.android.gms.internal.zzne;

public final class zzmv {
    private static IntentFilter zzaob = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static long zzaoc;
    private static float zzaod;

    static {
        zzaod = Float.NaN;
    }

    /*
     * Enabled aggressive block sorting
     */
    @TargetApi(value=20)
    public static int zzax(Context context) {
        int n2 = 1;
        if (context == null) return -1;
        if (context.getApplicationContext() == null) {
            return -1;
        }
        Intent intent = context.getApplicationContext().registerReceiver(null, zzaob);
        int n3 = intent == null ? 0 : intent.getIntExtra("plugged", 0);
        n3 = (n3 & 7) != 0 ? 1 : 0;
        if ((context = (PowerManager)context.getSystemService("power")) == null) {
            return -1;
        }
        boolean bl2 = zzne.zzsl() ? context.isInteractive() : context.isScreenOn();
        int n4 = bl2 ? 1 : 0;
        if (n3 != 0) {
            n3 = n2;
            return n4 << 1 | n3;
        }
        n3 = 0;
        return n4 << 1 | n3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static float zzay(Context context) {
        synchronized (zzmv.class) {
            float f2;
            if (SystemClock.elapsedRealtime() - zzaoc < 60000L && zzaod != Float.NaN) {
                f2 = zzaod;
                return f2;
            }
            if ((context = context.getApplicationContext().registerReceiver(null, zzaob)) != null) {
                int n2 = context.getIntExtra("level", -1);
                int n3 = context.getIntExtra("scale", -1);
                zzaod = (float)n2 / (float)n3;
            }
            zzaoc = SystemClock.elapsedRealtime();
            f2 = zzaod;
            return f2;
        }
    }
}

