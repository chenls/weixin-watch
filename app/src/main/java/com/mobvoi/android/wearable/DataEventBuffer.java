/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.wearable;

import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.common.data.DataBuffer;
import com.mobvoi.android.wearable.DataEvent;
import com.mobvoi.android.wearable.internal.DataHolder;
import java.util.ArrayList;

public class DataEventBuffer
extends DataBuffer<DataEvent>
implements Result {
    private Status c;

    public DataEventBuffer(DataHolder dataHolder) {
        super(dataHolder);
        this.c = new Status(dataHolder.a());
        this.a = dataHolder.c();
        if (this.a == null) {
            this.a = new ArrayList();
        }
    }

    @Override
    public Status getStatus() {
        return this.c;
    }
}

