/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 *  android.util.Pair
 */
package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.api.internal.zzx;
import com.google.android.gms.common.internal.zzq;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class zzb<R extends Result>
extends PendingResult<R> {
    private boolean zzL;
    private final Object zzagI = new Object();
    protected final zza<R> zzagJ;
    private final WeakReference<GoogleApiClient> zzagK;
    private final ArrayList<PendingResult.zza> zzagL;
    private ResultCallback<? super R> zzagM;
    private volatile boolean zzagN;
    private boolean zzagO;
    private boolean zzagP;
    private zzq zzagQ;
    private Integer zzagR;
    private volatile zzx<R> zzagS;
    private volatile R zzagy;
    private final CountDownLatch zzpJ = new CountDownLatch(1);

    @Deprecated
    protected zzb(Looper looper) {
        this.zzagL = new ArrayList();
        this.zzagJ = new zza(looper);
        this.zzagK = new WeakReference<Object>(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected zzb(GoogleApiClient googleApiClient) {
        this.zzagL = new ArrayList();
        Looper looper = googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper();
        this.zzagJ = new zza(looper);
        this.zzagK = new WeakReference<GoogleApiClient>(googleApiClient);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private R get() {
        R r2;
        boolean bl2 = true;
        Object object = this.zzagI;
        synchronized (object) {
            if (this.zzagN) {
                bl2 = false;
            }
            com.google.android.gms.common.internal.zzx.zza(bl2, (Object)"Result has already been consumed.");
            com.google.android.gms.common.internal.zzx.zza(this.isReady(), (Object)"Result is not ready.");
            r2 = this.zzagy;
            this.zzagy = null;
            this.zzagM = null;
            this.zzagN = true;
        }
        this.zzpf();
        return r2;
    }

    private void zzb(R object) {
        this.zzagy = object;
        this.zzagQ = null;
        this.zzpJ.countDown();
        object = this.zzagy.getStatus();
        if (this.zzagM != null) {
            this.zzagJ.zzph();
            if (!this.zzL) {
                this.zzagJ.zza(this.zzagM, (R)this.get());
            }
        }
        Iterator<PendingResult.zza> iterator = this.zzagL.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzu((Status)object);
        }
        this.zzagL.clear();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void zzc(Result result) {
        if (!(result instanceof Releasable)) return;
        try {
            ((Releasable)((Object)result)).release();
            return;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)"BasePendingResult", (String)("Unable to release " + result), (Throwable)runtimeException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final R await() {
        boolean bl2 = true;
        boolean bl3 = Looper.myLooper() != Looper.getMainLooper();
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"await must not be called on the UI thread");
        bl3 = !this.zzagN;
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Result has already been consumed");
        bl3 = this.zzagS == null ? bl2 : false;
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot await if then() has been called.");
        try {
            this.zzpJ.await();
        }
        catch (InterruptedException interruptedException) {
            this.zzx(Status.zzagD);
        }
        com.google.android.gms.common.internal.zzx.zza(this.isReady(), (Object)"Result is not ready.");
        return this.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final R await(long l2, TimeUnit timeUnit) {
        boolean bl2 = true;
        boolean bl3 = l2 <= 0L || Looper.myLooper() != Looper.getMainLooper();
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"await must not be called on the UI thread when time is greater than zero.");
        bl3 = !this.zzagN;
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Result has already been consumed.");
        bl3 = this.zzagS == null ? bl2 : false;
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot await if then() has been called.");
        try {
            if (!this.zzpJ.await(l2, timeUnit)) {
                this.zzx(Status.zzagF);
            }
        }
        catch (InterruptedException interruptedException) {
            this.zzx(Status.zzagD);
        }
        com.google.android.gms.common.internal.zzx.zza(this.isReady(), (Object)"Result is not ready.");
        return this.get();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void cancel() {
        Object object = this.zzagI;
        synchronized (object) {
            if (this.zzL || this.zzagN) {
                return;
            }
            zzq zzq2 = this.zzagQ;
            if (zzq2 != null) {
                try {
                    this.zzagQ.cancel();
                }
                catch (RemoteException remoteException) {}
            }
            zzb.zzc(this.zzagy);
            this.zzagM = null;
            this.zzL = true;
            this.zzb(this.zzc(Status.zzagG));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isCanceled() {
        Object object = this.zzagI;
        synchronized (object) {
            return this.zzL;
        }
    }

    public final boolean isReady() {
        return this.zzpJ.getCount() == 0L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        boolean bl2 = true;
        boolean bl3 = !this.zzagN;
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Result has already been consumed.");
        Object object = this.zzagI;
        synchronized (object) {
            bl3 = this.zzagS == null ? bl2 : false;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot set callbacks if then() has been called.");
            if (this.isCanceled()) {
                return;
            }
            if (this.zzagP && ((GoogleApiClient)this.zzagK.get() == null || !(resultCallback instanceof zzx))) {
                this.cancel();
                return;
            }
            if (this.isReady()) {
                this.zzagJ.zza(resultCallback, (R)this.get());
            } else {
                this.zzagM = resultCallback;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void setResultCallback(ResultCallback<? super R> resultCallback, long l2, TimeUnit timeUnit) {
        boolean bl2 = true;
        boolean bl3 = !this.zzagN;
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Result has already been consumed.");
        Object object = this.zzagI;
        synchronized (object) {
            bl3 = this.zzagS == null ? bl2 : false;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot set callbacks if then() has been called.");
            if (this.isCanceled()) {
                return;
            }
            if (this.zzagP && ((GoogleApiClient)this.zzagK.get() == null || !(resultCallback instanceof zzx))) {
                this.cancel();
                return;
            }
            if (this.isReady()) {
                this.zzagJ.zza(resultCallback, (R)this.get());
            } else {
                this.zzagM = resultCallback;
                this.zzagJ.zza(this, timeUnit.toMillis(l2));
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> object) {
        boolean bl2 = true;
        boolean bl3 = !this.zzagN;
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Result has already been consumed.");
        Object object2 = this.zzagI;
        synchronized (object2) {
            bl3 = this.zzagS == null;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot call then() twice.");
            bl3 = this.zzagM == null ? bl2 : false;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot call then() if callbacks are set.");
            this.zzagS = new zzx(this.zzagK);
            object = this.zzagS.then(object);
            if (this.isReady()) {
                this.zzagJ.zza(this.zzagS, this.get());
            } else {
                this.zzagM = this.zzagS;
            }
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void zza(PendingResult.zza zza2) {
        boolean bl2 = true;
        boolean bl3 = !this.zzagN;
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Result has already been consumed.");
        bl3 = zza2 != null ? bl2 : false;
        com.google.android.gms.common.internal.zzx.zzb(bl3, (Object)"Callback cannot be null.");
        Object object = this.zzagI;
        synchronized (object) {
            if (this.isReady()) {
                zza2.zzu(this.zzagy.getStatus());
            } else {
                this.zzagL.add(zza2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zza(R r2) {
        boolean bl2 = true;
        Object object = this.zzagI;
        synchronized (object) {
            if (this.zzagO || this.zzL) {
                zzb.zzc(r2);
                return;
            }
            boolean bl3 = !this.isReady();
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Results have already been set");
            bl3 = !this.zzagN ? bl2 : false;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Result has already been consumed");
            this.zzb(r2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void zza(zzq zzq2) {
        Object object = this.zzagI;
        synchronized (object) {
            this.zzagQ = zzq2;
            return;
        }
    }

    protected abstract R zzc(Status var1);

    @Override
    public Integer zzpa() {
        return this.zzagR;
    }

    protected void zzpf() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzpg() {
        Object object = this.zzagI;
        synchronized (object) {
            if ((GoogleApiClient)this.zzagK.get() == null) {
                this.cancel();
                return;
            }
            if (this.zzagM == null || this.zzagM instanceof zzx) {
                this.zzagP = true;
            } else {
                this.cancel();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void zzx(Status status) {
        Object object = this.zzagI;
        synchronized (object) {
            if (!this.isReady()) {
                this.zza(this.zzc(status));
                this.zzagO = true;
            }
            return;
        }
    }

    public static class zza<R extends Result>
    extends Handler {
        public zza() {
            this(Looper.getMainLooper());
        }

        public zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    Log.wtf((String)"BasePendingResult", (String)("Don't know how to handle message: " + message.what), (Throwable)new Exception());
                    return;
                }
                case 1: {
                    message = (Pair)message.obj;
                    this.zzb((ResultCallback)message.first, (Result)message.second);
                    return;
                }
                case 2: 
            }
            ((zzb)message.obj).zzx(Status.zzagF);
        }

        public void zza(ResultCallback<? super R> resultCallback, R r2) {
            this.sendMessage(this.obtainMessage(1, new Pair(resultCallback, r2)));
        }

        public void zza(zzb<R> zzb2, long l2) {
            this.sendMessageDelayed(this.obtainMessage(2, zzb2), l2);
        }

        protected void zzb(ResultCallback<? super R> resultCallback, R r2) {
            try {
                resultCallback.onResult(r2);
                return;
            }
            catch (RuntimeException runtimeException) {
                zzb.zzc(r2);
                throw runtimeException;
            }
        }

        public void zzph() {
            this.removeMessages(2);
        }
    }
}

