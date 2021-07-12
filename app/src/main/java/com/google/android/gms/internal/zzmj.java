/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.internal.zzml;

public class zzmj
extends zzj<zzml> {
    public zzmj(Context context, Looper looper, zzf zzf2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 39, zzf2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    protected /* synthetic */ IInterface zzW(IBinder iBinder) {
        return this.zzaW(iBinder);
    }

    protected zzml zzaW(IBinder iBinder) {
        return zzml.zza.zzaY(iBinder);
    }

    @Override
    public String zzgu() {
        return "com.google.android.gms.common.service.START";
    }

    @Override
    protected String zzgv() {
        return "com.google.android.gms.common.internal.service.ICommonService";
    }
}

