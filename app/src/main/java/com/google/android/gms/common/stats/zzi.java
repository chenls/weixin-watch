/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.stats.WakeLockEvent;
import com.google.android.gms.common.stats.zzc;
import com.google.android.gms.common.stats.zzd;
import com.google.android.gms.internal.zzmp;
import com.google.android.gms.internal.zzmv;
import java.util.List;

public class zzi {
    private static String TAG = "WakeLockTracker";
    private static zzi zzanY = new zzi();
    private static Integer zzanv;

    private static int getLogLevel() {
        try {
            if (zzmp.zzkr()) {
                return zzc.zzb.zzanz.get();
            }
            int n2 = zzd.LOG_LEVEL_OFF;
            return n2;
        }
        catch (SecurityException securityException) {
            return zzd.LOG_LEVEL_OFF;
        }
    }

    private static boolean zzav(Context context) {
        if (zzanv == null) {
            zzanv = zzi.getLogLevel();
        }
        return zzanv != zzd.LOG_LEVEL_OFF;
    }

    public static zzi zzrZ() {
        return zzanY;
    }

    public void zza(Context context, String string2, int n2, String string3, String string4, int n3, List<String> list) {
        this.zza(context, string2, n2, string3, string4, n3, list, 0L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(Context context, String object, int n2, String string2, String string3, int n3, List<String> list, long l2) {
        long l3;
        block7: {
            block6: {
                if (!zzi.zzav(context)) break block6;
                if (TextUtils.isEmpty((CharSequence)object)) {
                    Log.e((String)TAG, (String)("missing wakeLock key. " + (String)object));
                    return;
                }
                l3 = System.currentTimeMillis();
                if (7 == n2 || 8 == n2 || 10 == n2 || 11 == n2) break block7;
            }
            return;
        }
        object = new WakeLockEvent(l3, n2, string2, n3, list, (String)object, SystemClock.elapsedRealtime(), zzmv.zzax(context), string3, context.getPackageName(), zzmv.zzay(context), l2);
        try {
            context.startService(new Intent().setComponent(zzd.zzanF).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", (Parcelable)object));
            return;
        }
        catch (Exception exception) {
            Log.wtf((String)TAG, (Throwable)exception);
            return;
        }
    }
}

