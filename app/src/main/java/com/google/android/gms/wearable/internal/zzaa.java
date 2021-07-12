/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.DataItemAsset;

public class zzaa
implements DataItemAsset {
    private final String zzvs;
    private final String zzyv;

    public zzaa(DataItemAsset dataItemAsset) {
        this.zzyv = dataItemAsset.getId();
        this.zzvs = dataItemAsset.getDataItemKey();
    }

    @Override
    public /* synthetic */ Object freeze() {
        return this.zzIL();
    }

    @Override
    public String getDataItemKey() {
        return this.zzvs;
    }

    @Override
    public String getId() {
        return this.zzyv;
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataItemAssetEntity[");
        stringBuilder.append("@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        if (this.zzyv == null) {
            stringBuilder.append(",noid");
        } else {
            stringBuilder.append(",");
            stringBuilder.append(this.zzyv);
        }
        stringBuilder.append(", key=");
        stringBuilder.append(this.zzvs);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public DataItemAsset zzIL() {
        return this;
    }
}

