/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.internal.zzmf;
import com.google.android.gms.internal.zzmj;

abstract class zzmi<R extends Result>
extends zza.zza<R, zzmj> {
    public zzmi(GoogleApiClient googleApiClient) {
        super(zzmf.zzUI, googleApiClient);
    }

    static abstract class zza
    extends zzmi<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public Status zzb(Status status) {
            return status;
        }

        @Override
        public /* synthetic */ Result zzc(Status status) {
            return this.zzb(status);
        }
    }
}

