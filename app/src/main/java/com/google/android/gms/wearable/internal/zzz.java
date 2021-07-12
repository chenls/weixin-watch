/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.internal.zzaf;
import com.google.android.gms.wearable.internal.zzy;

public final class zzz
extends zzc
implements DataEvent {
    private final int zzaDQ;

    public zzz(DataHolder dataHolder, int n2, int n3) {
        super(dataHolder, n2);
        this.zzaDQ = n3;
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.zzIK();
    }

    @Override
    public DataItem getDataItem() {
        return new zzaf(this.zzahi, this.zzaje, this.zzaDQ);
    }

    @Override
    public int getType() {
        return this.getInteger("event_type");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        if (this.getType() == 1) {
            string2 = "changed";
            return "DataEventRef{ type=" + string2 + ", dataitem=" + this.getDataItem() + " }";
        }
        if (this.getType() == 2) {
            string2 = "deleted";
            return "DataEventRef{ type=" + string2 + ", dataitem=" + this.getDataItem() + " }";
        }
        string2 = "unknown";
        return "DataEventRef{ type=" + string2 + ", dataitem=" + this.getDataItem() + " }";
    }

    public DataEvent zzIK() {
        return new zzy(this);
    }
}

