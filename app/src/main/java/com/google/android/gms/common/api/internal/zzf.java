/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

public abstract class zzf
implements Releasable,
Result {
    protected final Status zzUX;
    protected final DataHolder zzahi;

    protected zzf(DataHolder dataHolder, Status status) {
        this.zzUX = status;
        this.zzahi = dataHolder;
    }

    @Override
    public Status getStatus() {
        return this.zzUX;
    }

    @Override
    public void release() {
        if (this.zzahi != null) {
            this.zzahi.close();
        }
    }
}

