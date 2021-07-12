/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;
import com.google.android.gms.internal.zzqu;

@Deprecated
public class zzqt
implements zzqu.zza {
    private final zzqu zzbdw;
    private boolean zzbdx;

    public zzqt(Context context, int n2) {
        this(context, n2, null);
    }

    public zzqt(Context context, int n2, String string2) {
        this(context, n2, string2, null, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public zzqt(Context context, int n2, String string2, String string3, boolean bl2) {
        String string4 = context != context.getApplicationContext() ? context.getClass().getName() : "OneTimePlayLogger";
        this.zzbdw = new zzqu(context, n2, string2, string3, this, bl2, string4);
        this.zzbdx = true;
    }

    private void zzER() {
        if (!this.zzbdx) {
            throw new IllegalStateException("Cannot reuse one-time logger after sending.");
        }
    }

    public void send() {
        this.zzER();
        this.zzbdw.start();
        this.zzbdx = false;
    }

    @Override
    public void zzES() {
        this.zzbdw.stop();
    }

    @Override
    public void zzET() {
        Log.w((String)"OneTimePlayLogger", (String)"logger connection failed");
    }

    public void zza(String string2, byte[] byArray, String ... stringArray) {
        this.zzER();
        this.zzbdw.zzb(string2, byArray, stringArray);
    }

    @Override
    public void zzc(PendingIntent pendingIntent) {
        Log.w((String)"OneTimePlayLogger", (String)("logger connection failed: " + pendingIntent));
    }
}

