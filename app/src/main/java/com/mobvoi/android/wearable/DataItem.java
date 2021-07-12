/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.mobvoi.android.wearable;

import android.net.Uri;
import com.mobvoi.android.common.data.Freezable;
import com.mobvoi.android.wearable.DataItemAsset;
import java.util.Map;

public interface DataItem
extends Freezable<DataItem> {
    public Map<String, DataItemAsset> getAssets();

    public byte[] getData();

    public Uri getUri();

    public DataItem setData(byte[] var1);
}

