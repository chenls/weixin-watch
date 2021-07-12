/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 */
package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import com.google.android.gms.common.zzc;

abstract class zzn
extends BroadcastReceiver {
    protected Context mContext;

    zzn() {
    }

    @Nullable
    public static <T extends zzn> T zza(Context context, T t2) {
        return zzn.zza(context, t2, zzc.zzoK());
    }

    @Nullable
    public static <T extends zzn> T zza(Context context, T t2, zzc zzc2) {
        Object object = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        object.addDataScheme("package");
        context.registerReceiver(t2, object);
        t2.mContext = context;
        object = t2;
        if (!zzc2.zzi(context, "com.google.android.gms")) {
            t2.zzpJ();
            t2.unregister();
            object = null;
        }
        return (T)object;
    }

    public void onReceive(Context object, Intent intent) {
        intent = intent.getData();
        object = null;
        if (intent != null) {
            object = intent.getSchemeSpecificPart();
        }
        if ("com.google.android.gms".equals(object)) {
            this.zzpJ();
            this.unregister();
        }
    }

    public void unregister() {
        synchronized (this) {
            if (this.mContext != null) {
                this.mContext.unregisterReceiver((BroadcastReceiver)this);
            }
            this.mContext = null;
            return;
        }
    }

    protected abstract void zzpJ();
}

