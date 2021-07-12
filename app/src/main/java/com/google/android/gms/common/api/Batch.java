/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResult;
import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzb;
import java.util.ArrayList;
import java.util.List;

public final class Batch
extends zzb<BatchResult> {
    private int zzafZ;
    private boolean zzaga;
    private boolean zzagb;
    private final PendingResult<?>[] zzagc;
    private final Object zzpV = new Object();

    /*
     * Enabled aggressive block sorting
     */
    private Batch(List<PendingResult<?>> list, GoogleApiClient pendingResult) {
        super((GoogleApiClient)((Object)pendingResult));
        this.zzafZ = list.size();
        this.zzagc = new PendingResult[this.zzafZ];
        if (list.isEmpty()) {
            this.zza(new BatchResult(Status.zzagC, this.zzagc));
            return;
        } else {
            for (int i2 = 0; i2 < list.size(); ++i2) {
                pendingResult = list.get(i2);
                this.zzagc[i2] = pendingResult;
                pendingResult.zza(new PendingResult.zza(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void zzu(Status status) {
                        Object object = Batch.this.zzpV;
                        synchronized (object) {
                            if (Batch.this.isCanceled()) {
                                return;
                            }
                            if (status.isCanceled()) {
                                Batch.zza(Batch.this, true);
                            } else if (!status.isSuccess()) {
                                Batch.zzb(Batch.this, true);
                            }
                            Batch.zzb(Batch.this);
                            if (Batch.this.zzafZ == 0) {
                                if (Batch.this.zzagb) {
                                    Batch.super.cancel();
                                } else {
                                    status = Batch.this.zzaga ? new Status(13) : Status.zzagC;
                                    Batch.this.zza(new BatchResult(status, Batch.this.zzagc));
                                }
                            }
                            return;
                        }
                    }
                });
            }
        }
    }

    static /* synthetic */ boolean zza(Batch batch, boolean bl2) {
        batch.zzagb = bl2;
        return bl2;
    }

    static /* synthetic */ int zzb(Batch batch) {
        int n2 = batch.zzafZ;
        batch.zzafZ = n2 - 1;
        return n2;
    }

    static /* synthetic */ boolean zzb(Batch batch, boolean bl2) {
        batch.zzaga = bl2;
        return bl2;
    }

    @Override
    public void cancel() {
        super.cancel();
        PendingResult<?>[] pendingResultArray = this.zzagc;
        int n2 = pendingResultArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            pendingResultArray[i2].cancel();
        }
    }

    public BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zzagc);
    }

    @Override
    public /* synthetic */ Result zzc(Status status) {
        return this.createFailedResult(status);
    }

    public static final class Builder {
        private GoogleApiClient zzaaj;
        private List<PendingResult<?>> zzage = new ArrayList();

        public Builder(GoogleApiClient googleApiClient) {
            this.zzaaj = googleApiClient;
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken batchResultToken = new BatchResultToken(this.zzage.size());
            this.zzage.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.zzage, this.zzaaj);
        }
    }
}

