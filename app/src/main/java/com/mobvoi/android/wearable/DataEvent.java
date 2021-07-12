/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.wearable;

import com.mobvoi.android.common.data.Freezable;
import com.mobvoi.android.wearable.DataItem;

public interface DataEvent
extends Freezable<DataEvent> {
    public static final int TYPE_CHANGED = 1;
    public static final int TYPE_DELETED = 2;

    public DataItem getDataItem();

    public int getType();
}

