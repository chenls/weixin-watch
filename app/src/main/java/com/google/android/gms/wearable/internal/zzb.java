/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.internal.zzbp;
import com.google.android.gms.wearable.internal.zzi;

final class zzb<T>
extends zzi<Status> {
    private T mListener;
    private zzq<T> zzbbi;
    private zza<T> zzbrA;

    private zzb(GoogleApiClient googleApiClient, T t2, zzq<T> zzq2, zza<T> zza2) {
        super(googleApiClient);
        this.mListener = zzx.zzz(t2);
        this.zzbbi = zzx.zzz(zzq2);
        this.zzbrA = zzx.zzz(zza2);
    }

    static <T> PendingResult<Status> zza(GoogleApiClient googleApiClient, zza<T> zza2, T t2) {
        return googleApiClient.zza(new zzb<T>(googleApiClient, t2, googleApiClient.zzr(t2), zza2));
    }

    @Override
    protected void zza(zzbp zzbp2) throws RemoteException {
        this.zzbrA.zza(zzbp2, this, this.mListener, this.zzbbi);
        this.mListener = null;
        this.zzbbi = null;
    }

    protected Status zzb(Status status) {
        this.mListener = null;
        this.zzbbi = null;
        return status;
    }

    @Override
    protected /* synthetic */ Result zzc(Status status) {
        return this.zzb(status);
    }

    static interface zza<T> {
        public void zza(zzbp var1, zza.zzb<Status> var2, T var3, zzq<T> var4) throws RemoteException;
    }
}

