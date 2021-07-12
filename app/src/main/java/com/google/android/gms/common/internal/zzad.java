/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;

public class zzad<T extends IInterface>
extends zzj<T> {
    private final Api.zzd<T> zzamx;

    public zzad(Context context, Looper looper, int n2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzf zzf2, Api.zzd zzd2) {
        super(context, looper, n2, zzf2, connectionCallbacks, onConnectionFailedListener);
        this.zzamx = zzd2;
    }

    @Override
    protected T zzW(IBinder iBinder) {
        return this.zzamx.zzW(iBinder);
    }

    @Override
    protected void zzc(int n2, T t2) {
        this.zzamx.zza(n2, t2);
    }

    @Override
    protected String zzgu() {
        return this.zzamx.zzgu();
    }

    @Override
    protected String zzgv() {
        return this.zzamx.zzgv();
    }
}

