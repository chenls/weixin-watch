/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzf;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.internal.zzz;

public class DataEventBuffer
extends zzf<DataEvent>
implements Result {
    private final Status zzUX;

    public DataEventBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.zzUX = new Status(dataHolder.getStatusCode());
    }

    @Override
    public Status getStatus() {
        return this.zzUX;
    }

    @Override
    protected /* synthetic */ Object zzk(int n2, int n3) {
        return this.zzw(n2, n3);
    }

    @Override
    protected String zzqg() {
        return "path";
    }

    protected DataEvent zzw(int n2, int n3) {
        return new zzz(this.zzahi, n2, n3);
    }
}

