/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.internal.zzab;

@KeepName
public class DataItemAssetParcelable
implements SafeParcelable,
DataItemAsset {
    public static final Parcelable.Creator<DataItemAssetParcelable> CREATOR = new zzab();
    final int mVersionCode;
    private final String zzvs;
    private final String zzyv;

    DataItemAssetParcelable(int n2, String string2, String string3) {
        this.mVersionCode = n2;
        this.zzyv = string2;
        this.zzvs = string3;
    }

    public DataItemAssetParcelable(DataItemAsset dataItemAsset) {
        this.mVersionCode = 1;
        this.zzyv = zzx.zzz(dataItemAsset.getId());
        this.zzvs = zzx.zzz(dataItemAsset.getDataItemKey());
    }

    public int describeContents() {
        return 0;
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
        stringBuilder.append("DataItemAssetParcelable[");
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

    public void writeToParcel(Parcel parcel, int n2) {
        zzab.zza(this, parcel, n2);
    }

    public DataItemAsset zzIL() {
        return this;
    }
}

