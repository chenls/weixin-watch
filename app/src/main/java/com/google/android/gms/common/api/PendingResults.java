/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzr;
import com.google.android.gms.common.api.internal.zzv;
import com.google.android.gms.common.internal.zzx;

public final class PendingResults {
    private PendingResults() {
    }

    public static PendingResult<Status> canceledPendingResult() {
        zzv zzv2 = new zzv(Looper.getMainLooper());
        zzv2.cancel();
        return zzv2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <R extends Result> PendingResult<R> canceledPendingResult(R object) {
        zzx.zzb(object, (Object)"Result must not be null");
        boolean bl2 = object.getStatus().getStatusCode() == 16;
        zzx.zzb(bl2, (Object)"Status code must be CommonStatusCodes.CANCELED");
        object = new zza<Object>(object);
        ((com.google.android.gms.common.api.internal.zzb)object).cancel();
        return object;
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r2) {
        zzx.zzb(r2, (Object)"Result must not be null");
        zzc<R> zzc2 = new zzc<R>(null);
        zzc2.zza(r2);
        return new zzr(zzc2);
    }

    public static PendingResult<Status> immediatePendingResult(Status status) {
        zzx.zzb(status, (Object)"Result must not be null");
        zzv zzv2 = new zzv(Looper.getMainLooper());
        zzv2.zza(status);
        return zzv2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <R extends Result> PendingResult<R> zza(R r2, GoogleApiClient zzb2) {
        zzx.zzb(r2, (Object)"Result must not be null");
        boolean bl2 = !r2.getStatus().isSuccess();
        zzx.zzb(bl2, (Object)"Status code must not be SUCCESS");
        zzb2 = new zzb<R>((GoogleApiClient)((Object)zzb2), r2);
        zzb2.zza(r2);
        return zzb2;
    }

    public static PendingResult<Status> zza(Status status, GoogleApiClient object) {
        zzx.zzb(status, (Object)"Result must not be null");
        object = new zzv((GoogleApiClient)object);
        ((com.google.android.gms.common.api.internal.zzb)object).zza(status);
        return object;
    }

    public static <R extends Result> OptionalPendingResult<R> zzb(R r2, GoogleApiClient object) {
        zzx.zzb(r2, (Object)"Result must not be null");
        object = new zzc((GoogleApiClient)object);
        ((com.google.android.gms.common.api.internal.zzb)object).zza(r2);
        return new zzr(object);
    }

    private static final class zza<R extends Result>
    extends com.google.android.gms.common.api.internal.zzb<R> {
        private final R zzagx;

        public zza(R r2) {
            super(Looper.getMainLooper());
            this.zzagx = r2;
        }

        @Override
        protected R zzc(Status status) {
            if (status.getStatusCode() != this.zzagx.getStatus().getStatusCode()) {
                throw new UnsupportedOperationException("Creating failed results is not supported");
            }
            return this.zzagx;
        }
    }

    private static final class zzb<R extends Result>
    extends com.google.android.gms.common.api.internal.zzb<R> {
        private final R zzagy;

        public zzb(GoogleApiClient googleApiClient, R r2) {
            super(googleApiClient);
            this.zzagy = r2;
        }

        @Override
        protected R zzc(Status status) {
            return this.zzagy;
        }
    }

    private static final class zzc<R extends Result>
    extends com.google.android.gms.common.api.internal.zzb<R> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override
        protected R zzc(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }
}

