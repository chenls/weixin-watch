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
import com.google.android.gms.wearable.internal.DataItemParcelable;
import com.google.android.gms.wearable.internal.zzbf;

public class PutDataResponse
implements SafeParcelable {
    public static final Parcelable.Creator<PutDataResponse> CREATOR = new zzbf();
    public final int statusCode;
    public final int versionCode;
    public final DataItemParcelable zzbsJ;

    PutDataResponse(int n2, int n3, DataItemParcelable dataItemParcelable) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsJ = dataItemParcelable;
    }

    public PutDataResponse(int n2, DataItemParcelable dataItemParcelable) {
        this(1, n2, dataItemParcelable);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzbf.zza(this, parcel, n2);
    }
}

