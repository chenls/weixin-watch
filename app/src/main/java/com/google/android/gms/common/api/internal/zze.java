/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.data.DataHolder;

public abstract class zze<L>
implements zzq.zzb<L> {
    private final DataHolder zzahi;

    protected zze(DataHolder dataHolder) {
        this.zzahi = dataHolder;
    }

    protected abstract void zza(L var1, DataHolder var2);

    @Override
    public void zzpr() {
        if (this.zzahi != null) {
            this.zzahi.close();
        }
    }

    @Override
    public final void zzt(L l2) {
        this.zza(l2, this.zzahi);
    }
}

