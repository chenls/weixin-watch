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
import com.google.android.gms.wearable.internal.CapabilityInfoParcelable;
import com.google.android.gms.wearable.internal.zzai;

public class GetCapabilityResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetCapabilityResponse> CREATOR = new zzai();
    public final int statusCode;
    public final int versionCode;
    public final CapabilityInfoParcelable zzbsB;

    GetCapabilityResponse(int n2, int n3, CapabilityInfoParcelable capabilityInfoParcelable) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsB = capabilityInfoParcelable;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzai.zza(this, parcel, n2);
    }
}

