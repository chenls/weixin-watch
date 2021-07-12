/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzb;

public class zzv
extends zzb<Status> {
    @Deprecated
    public zzv(Looper looper) {
        super(looper);
    }

    public zzv(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected Status zzb(Status status) {
        return status;
    }

    @Override
    protected /* synthetic */ Result zzc(Status status) {
        return this.zzb(status);
    }
}

