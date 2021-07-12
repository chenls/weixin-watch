/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.internal.zzlw;
import com.google.android.gms.internal.zzlx;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzmt;
import com.google.android.gms.internal.zzsu;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class zzlv
implements com.google.android.gms.clearcut.zzc {
    private static final Object zzafn = new Object();
    private static final zze zzafo = new zze();
    private static final long zzafp = TimeUnit.MILLISECONDS.convert(2L, TimeUnit.MINUTES);
    private GoogleApiClient zzaaj = null;
    private final zza zzafq;
    private final Object zzafr = new Object();
    private long zzafs = 0L;
    private final long zzaft;
    private ScheduledFuture<?> zzafu = null;
    private final Runnable zzafv = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object = zzlv.this.zzafr;
            synchronized (object) {
                if (zzlv.this.zzafs <= zzlv.this.zzqW.elapsedRealtime() && zzlv.this.zzaaj != null) {
                    Log.i((String)"ClearcutLoggerApiImpl", (String)"disconnect managed GoogleApiClient");
                    zzlv.this.zzaaj.disconnect();
                    zzlv.zza(zzlv.this, null);
                }
                return;
            }
        }
    };
    private final zzmq zzqW;

    public zzlv() {
        this(new zzmt(), zzafp, new zzb());
    }

    public zzlv(zzmq zzmq2, long l2, zza zza2) {
        this.zzqW = zzmq2;
        this.zzaft = l2;
        this.zzafq = zza2;
    }

    static /* synthetic */ GoogleApiClient zza(zzlv zzlv2, GoogleApiClient googleApiClient) {
        zzlv2.zzaaj = googleApiClient;
        return googleApiClient;
    }

    private static void zza(LogEventParcelable logEventParcelable) {
        if (logEventParcelable.zzafl != null && logEventParcelable.zzafk.zzbuY.length == 0) {
            logEventParcelable.zzafk.zzbuY = logEventParcelable.zzafl.zzoF();
        }
        if (logEventParcelable.zzafm != null && logEventParcelable.zzafk.zzbvf.length == 0) {
            logEventParcelable.zzafk.zzbvf = logEventParcelable.zzafm.zzoF();
        }
        logEventParcelable.zzafi = zzsu.toByteArray(logEventParcelable.zzafk);
    }

    private zzd zzb(GoogleApiClient object, LogEventParcelable logEventParcelable) {
        zzafo.zzoH();
        object = new zzd(logEventParcelable, (GoogleApiClient)object);
        ((zza.zza)object).zza(new PendingResult.zza(){

            @Override
            public void zzu(Status status) {
                zzafo.zzoI();
            }
        });
        return object;
    }

    @Override
    public PendingResult<Status> zza(GoogleApiClient googleApiClient, LogEventParcelable logEventParcelable) {
        zzlv.zza(logEventParcelable);
        return googleApiClient.zza(this.zzb(googleApiClient, logEventParcelable));
    }

    @Override
    public boolean zza(GoogleApiClient googleApiClient, long l2, TimeUnit timeUnit) {
        try {
            boolean bl2 = zzafo.zza(l2, timeUnit);
            return bl2;
        }
        catch (InterruptedException interruptedException) {
            Log.e((String)"ClearcutLoggerApiImpl", (String)"flush interrupted");
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public static interface zza {
    }

    public static class zzb
    implements zza {
    }

    static abstract class zzc<R extends Result>
    extends zza.zza<R, zzlw> {
        public zzc(GoogleApiClient googleApiClient) {
            super(com.google.android.gms.clearcut.zzb.zzUI, googleApiClient);
        }
    }

    final class zzd
    extends zzc<Status> {
        private final LogEventParcelable zzafx;

        zzd(LogEventParcelable logEventParcelable, GoogleApiClient googleApiClient) {
            super(googleApiClient);
            this.zzafx = logEventParcelable;
        }

        public boolean equals(Object object) {
            if (!(object instanceof zzd)) {
                return false;
            }
            object = (zzd)object;
            return this.zzafx.equals(((zzd)object).zzafx);
        }

        public String toString() {
            return "MethodImpl(" + this.zzafx + ")";
        }

        @Override
        protected void zza(zzlw zzlw2) throws RemoteException {
            zzlx.zza zza2 = new zzlx.zza(){

                @Override
                public void zzv(Status status) {
                    zzd.this.zza(status);
                }
            };
            try {
                zzlv.zza(this.zzafx);
            }
            catch (Throwable throwable) {
                Log.e((String)"ClearcutLoggerApiImpl", (String)("MessageNanoProducer " + this.zzafx.zzafl.toString() + " threw: " + throwable.toString()));
                return;
            }
            zzlw2.zza(zza2, this.zzafx);
        }

        protected Status zzb(Status status) {
            return status;
        }

        @Override
        protected /* synthetic */ Result zzc(Status status) {
            return this.zzb(status);
        }
    }

    private static final class zze {
        private int mSize = 0;

        private zze() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean zza(long l2, TimeUnit timeUnit) throws InterruptedException {
            long l3 = System.currentTimeMillis();
            l2 = TimeUnit.MILLISECONDS.convert(l2, timeUnit);
            synchronized (this) {
                while (this.mSize != 0) {
                    if (l2 <= 0L) {
                        return false;
                    }
                    this.wait(l2);
                    long l4 = System.currentTimeMillis();
                    l2 -= l4 - l3;
                }
                return true;
            }
        }

        public void zzoH() {
            synchronized (this) {
                ++this.mSize;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void zzoI() {
            synchronized (this) {
                if (this.mSize == 0) {
                    throw new RuntimeException("too many decrements");
                }
                --this.mSize;
                if (this.mSize == 0) {
                    this.notifyAll();
                }
                return;
            }
        }
    }
}

