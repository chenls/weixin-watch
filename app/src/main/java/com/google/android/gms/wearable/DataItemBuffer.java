/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzf;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.internal.zzaf;

public class DataItemBuffer
extends zzf<DataItem>
implements Result {
    private final Status zzUX;

    public DataItemBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.zzUX = new Status(dataHolder.getStatusCode());
    }

    @Override
    public Status getStatus() {
        return this.zzUX;
    }

    @Override
    protected /* synthetic */ Object zzk(int n2, int n3) {
        return this.zzx(n2, n3);
    }

    @Override
    protected String zzqg() {
        return "path";
    }

    protected DataItem zzx(int n2, int n3) {
        return new zzaf(this.zzahi, n2, n3);
    }
}

