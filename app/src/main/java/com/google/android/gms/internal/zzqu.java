/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.playlog.internal.LogEvent;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import com.google.android.gms.playlog.internal.zzd;
import com.google.android.gms.playlog.internal.zzf;

@Deprecated
public class zzqu {
    private final zzf zzbdy;
    private PlayLoggerContext zzbdz;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public zzqu(Context context, int n2, String string2, String string3, zza zza2, boolean bl2, String string4) {
        String string5 = context.getPackageName();
        int n3 = 0;
        try {
            int n4;
            n3 = n4 = context.getPackageManager().getPackageInfo((String)string5, (int)0).versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.wtf((String)"PlayLogger", (String)"This can't happen.", (Throwable)nameNotFoundException);
        }
        this.zzbdz = new PlayLoggerContext(string5, n3, n2, string2, string3, bl2);
        this.zzbdy = new zzf(context, context.getMainLooper(), new zzd(zza2), new com.google.android.gms.common.internal.zzf(null, null, null, 49, null, string5, string4, null));
    }

    public void start() {
        this.zzbdy.start();
    }

    public void stop() {
        this.zzbdy.stop();
    }

    public void zza(long l2, String string2, byte[] byArray, String ... stringArray) {
        this.zzbdy.zzb(this.zzbdz, new LogEvent(l2, 0L, string2, byArray, stringArray));
    }

    public void zzb(String string2, byte[] byArray, String ... stringArray) {
        this.zza(System.currentTimeMillis(), string2, byArray, stringArray);
    }

    public static interface zza {
        public void zzES();

        public void zzET();

        public void zzc(PendingIntent var1);
    }
}

