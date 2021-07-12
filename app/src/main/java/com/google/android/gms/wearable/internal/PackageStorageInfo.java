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
import com.google.android.gms.wearable.internal.zzbe;

public class PackageStorageInfo
implements SafeParcelable {
    public static final Parcelable.Creator<PackageStorageInfo> CREATOR = new zzbe();
    public final String label;
    public final String packageName;
    public final int versionCode;
    public final long zzbta;

    PackageStorageInfo(int n2, String string2, String string3, long l2) {
        this.versionCode = n2;
        this.packageName = string2;
        this.label = string3;
        this.zzbta = l2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzbe.zza(this, parcel, n2);
    }
}

