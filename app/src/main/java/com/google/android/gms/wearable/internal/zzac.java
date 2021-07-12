/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.internal.zzaa;

public class zzac
extends zzc
implements DataItemAsset {
    public zzac(DataHolder dataHolder, int n2) {
        super(dataHolder, n2);
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.zzIL();
    }

    @Override
    public String getDataItemKey() {
        return this.getString("asset_key");
    }

    @Override
    public String getId() {
        return this.getString("asset_id");
    }

    public DataItemAsset zzIL() {
        return new zzaa(this);
    }
}

