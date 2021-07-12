/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.TimeUnit;

public final class BatchResult
implements Result {
    private final Status zzUX;
    private final PendingResult<?>[] zzagc;

    BatchResult(Status status, PendingResult<?>[] pendingResultArray) {
        this.zzUX = status;
        this.zzagc = pendingResultArray;
    }

    @Override
    public Status getStatus() {
        return this.zzUX;
    }

    /*
     * Enabled aggressive block sorting
     */
    public <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        boolean bl2 = batchResultToken.mId < this.zzagc.length;
        zzx.zzb(bl2, (Object)"The result token does not belong to this batch");
        return (R)this.zzagc[batchResultToken.mId].await(0L, TimeUnit.MILLISECONDS);
    }
}

