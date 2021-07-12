/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.internal.zzlx;
import com.google.android.gms.internal.zzly;

public class zzlw
extends zzj<zzly> {
    public zzlw(Context context, Looper looper, zzf zzf2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 40, zzf2, connectionCallbacks, onConnectionFailedListener);
    }

    @Override
    protected /* synthetic */ IInterface zzW(IBinder iBinder) {
        return this.zzaK(iBinder);
    }

    public void zza(zzlx zzlx2, LogEventParcelable logEventParcelable) throws RemoteException {
        ((zzly)this.zzqJ()).zza(zzlx2, logEventParcelable);
    }

    protected zzly zzaK(IBinder iBinder) {
        return zzly.zza.zzaM(iBinder);
    }

    @Override
    protected String zzgu() {
        return "com.google.android.gms.clearcut.service.START";
    }

    @Override
    protected String zzgv() {
        return "com.google.android.gms.clearcut.internal.IClearcutLoggerService";
    }
}

