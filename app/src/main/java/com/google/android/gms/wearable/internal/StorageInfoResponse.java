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
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.internal.PackageStorageInfo;
import com.google.android.gms.wearable.internal.zzbl;
import java.util.List;

public class StorageInfoResponse
implements SafeParcelable {
    public static final Parcelable.Creator<StorageInfoResponse> CREATOR = new zzbl();
    public final int statusCode;
    public final int versionCode;
    public final long zzbta;
    public final List<PackageStorageInfo> zzbtc;

    StorageInfoResponse(int n2, int n3, long l2, List<PackageStorageInfo> list) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbta = l2;
        this.zzbtc = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzbl.zza(this, parcel, n2);
    }
}

