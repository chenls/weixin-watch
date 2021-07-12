/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.api.internal.zzs;
import com.google.android.gms.common.api.internal.zzt;
import java.lang.ref.WeakReference;

public class zzx<R extends Result>
extends TransformedResult<R>
implements ResultCallback<R> {
    private final Object zzagI = new Object();
    private final WeakReference<GoogleApiClient> zzagK;
    private ResultTransform<? super R, ? extends Result> zzaiN = null;
    private zzx<? extends Result> zzaiO = null;
    private ResultCallbacks<? super R> zzaiP = null;
    private PendingResult<R> zzaiQ = null;
    private Status zzaiR = null;
    private final zza zzaiS;

    /*
     * Enabled aggressive block sorting
     */
    public zzx(WeakReference<GoogleApiClient> object) {
        com.google.android.gms.common.internal.zzx.zzb(object, (Object)"GoogleApiClient reference must not be null");
        this.zzagK = object;
        object = (GoogleApiClient)this.zzagK.get();
        object = object != null ? ((GoogleApiClient)object).getLooper() : Looper.getMainLooper();
        this.zzaiS = new zza((Looper)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void zzc(Result result) {
        if (!(result instanceof Releasable)) return;
        try {
            ((Releasable)((Object)result)).release();
            return;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)"TransformedResultImpl", (String)("Unable to release " + result), (Throwable)runtimeException);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzpT() {
        block6: {
            block5: {
                if (this.zzaiN == null && this.zzaiP == null) break block5;
                GoogleApiClient googleApiClient = (GoogleApiClient)this.zzagK.get();
                if (this.zzaiN != null && googleApiClient != null) {
                    googleApiClient.zza(this);
                }
                if (this.zzaiR != null) {
                    this.zzz(this.zzaiR);
                    return;
                }
                if (this.zzaiQ != null) break block6;
            }
            return;
        }
        this.zzaiQ.setResultCallback(this);
    }

    private boolean zzpV() {
        GoogleApiClient googleApiClient = (GoogleApiClient)this.zzagK.get();
        return this.zzaiP != null && googleApiClient != null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzy(Status status) {
        Object object = this.zzagI;
        synchronized (object) {
            this.zzaiR = status;
            this.zzz(this.zzaiR);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzz(Status status) {
        Object object = this.zzagI;
        synchronized (object) {
            if (this.zzaiN != null) {
                status = this.zzaiN.onFailure(status);
                com.google.android.gms.common.internal.zzx.zzb(status, (Object)"onFailure must not return null");
                super.zzy(status);
            } else if (this.zzpV()) {
                this.zzaiP.onFailure(status);
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
    public void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        boolean bl2 = true;
        Object object = this.zzagI;
        synchronized (object) {
            boolean bl3 = this.zzaiP == null;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot call andFinally() twice.");
            bl3 = this.zzaiN == null ? bl2 : false;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzaiP = resultCallbacks;
            this.zzpT();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onResult(R r2) {
        Object object = this.zzagI;
        synchronized (object) {
            block7: {
                block6: {
                    if (!r2.getStatus().isSuccess()) break block6;
                    if (this.zzaiN != null) {
                        zzs.zzpN().submit(new Runnable((Result)r2){
                            final /* synthetic */ Result zzaiT;
                            {
                                this.zzaiT = result;
                            }

                            /*
                             * Enabled aggressive block sorting
                             * Enabled unnecessary exception pruning
                             * Enabled aggressive exception aggregation
                             */
                            @Override
                            @WorkerThread
                            public void run() {
                                try {
                                    PendingResult pendingResult = zzx.this.zzaiN.onSuccess(this.zzaiT);
                                    zzx.this.zzaiS.sendMessage(zzx.this.zzaiS.obtainMessage(0, pendingResult));
                                    return;
                                }
                                catch (RuntimeException runtimeException) {
                                    zzx.this.zzaiS.sendMessage(zzx.this.zzaiS.obtainMessage(1, runtimeException));
                                    return;
                                }
                                finally {
                                    zzx.this.zzc(this.zzaiT);
                                    GoogleApiClient googleApiClient = (GoogleApiClient)zzx.this.zzagK.get();
                                    if (googleApiClient == null) return;
                                    googleApiClient.zzb(zzx.this);
                                }
                            }
                        });
                        break block7;
                    } else if (this.zzpV()) {
                        this.zzaiP.onSuccess(r2);
                    }
                    break block7;
                }
                this.zzy(r2.getStatus());
                this.zzc((Result)r2);
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
    @NonNull
    public <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> object) {
        boolean bl2 = true;
        Object object2 = this.zzagI;
        synchronized (object2) {
            boolean bl3 = this.zzaiN == null;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot call then() twice.");
            bl3 = this.zzaiP == null ? bl2 : false;
            com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzaiN = object;
            this.zzaiO = object = new zzx<R>(this.zzagK);
            this.zzpT();
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(PendingResult<?> pendingResult) {
        Object object = this.zzagI;
        synchronized (object) {
            this.zzaiQ = pendingResult;
            this.zzpT();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void zzpU() {
        Object object = this.zzagI;
        synchronized (object) {
            this.zzaiP = null;
            return;
        }
    }

    private final class zza
    extends Handler {
        public zza(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void handleMessage(Message object) {
            switch (((Message)object).what) {
                default: {
                    Log.e((String)"TransformedResultImpl", (String)("TransformationResultHandler received unknown message type: " + ((Message)object).what));
                    return;
                }
                case 0: {
                    PendingResult pendingResult = (PendingResult)((Message)object).obj;
                    object = zzx.this.zzagI;
                    synchronized (object) {
                        if (pendingResult == null) {
                            ((zzx)zzx.this.zzaiO).zzy(new Status(13, "Transform returned null"));
                        } else if (pendingResult instanceof zzt) {
                            ((zzx)zzx.this.zzaiO).zzy(((zzt)pendingResult).getStatus());
                        } else {
                            zzx.this.zzaiO.zza(pendingResult);
                        }
                        return;
                    }
                }
                case 1: 
            }
            object = (RuntimeException)((Message)object).obj;
            Log.e((String)"TransformedResultImpl", (String)("Runtime exception on the transformation worker thread: " + ((Throwable)object).getMessage()));
            throw object;
        }
    }
}

