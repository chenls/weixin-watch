/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;

public class zzy
implements DataEvent {
    private int zzabB;
    private DataItem zzbsv;

    public zzy(DataEvent dataEvent) {
        this.zzabB = dataEvent.getType();
        this.zzbsv = (DataItem)dataEvent.getDataItem().freeze();
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.zzIK();
    }

    @Override
    public DataItem getDataItem() {
        return this.zzbsv;
    }

    @Override
    public int getType() {
        return this.zzabB;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        if (this.getType() == 1) {
            string2 = "changed";
            return "DataEventEntity{ type=" + string2 + ", dataitem=" + this.getDataItem() + " }";
        }
        if (this.getType() == 2) {
            string2 = "deleted";
            return "DataEventEntity{ type=" + string2 + ", dataitem=" + this.getDataItem() + " }";
        }
        string2 = "unknown";
        return "DataEventEntity{ type=" + string2 + ", dataitem=" + this.getDataItem() + " }";
    }

    public DataEvent zzIK() {
        return this;
    }
}

