/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzb;

public abstract class ResultCallbacks<R extends Result>
implements ResultCallback<R> {
    public abstract void onFailure(@NonNull Status var1);

    @Override
    public final void onResult(@NonNull R r2) {
        Status status = r2.getStatus();
        if (status.isSuccess()) {
            this.onSuccess(r2);
            return;
        }
        this.onFailure(status);
        zzb.zzc(r2);
    }

    public abstract void onSuccess(@NonNull R var1);
}

