/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.wearable;

import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.common.data.DataBuffer;
import com.mobvoi.android.wearable.DataItem;
import com.mobvoi.android.wearable.internal.DataHolder;
import java.util.ArrayList;

public class DataItemBuffer
extends DataBuffer<DataItem>
implements Result {
    private final Status c;

    public DataItemBuffer(Status status) {
        this.c = status;
        this.a = new ArrayList();
    }

    public DataItemBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.c = new Status(dataHolder.a());
        this.a = dataHolder.b();
        if (this.a == null) {
            this.a = new ArrayList();
        }
    }

    @Override
    public Status getStatus() {
        return this.c;
    }
}

