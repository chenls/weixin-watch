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
import com.google.android.gms.wearable.ConnectionConfiguration;
import com.google.android.gms.wearable.internal.zzap;

public class GetConfigsResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetConfigsResponse> CREATOR = new zzap();
    public final int statusCode;
    public final int versionCode;
    public final ConnectionConfiguration[] zzbsH;

    GetConfigsResponse(int n2, int n3, ConnectionConfiguration[] connectionConfigurationArray) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsH = connectionConfigurationArray;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzap.zza(this, parcel, n2);
    }
}

