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
import com.google.android.gms.wearable.internal.zzah;
import java.util.List;

public class GetAllCapabilitiesResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetAllCapabilitiesResponse> CREATOR = new zzah();
    public final int statusCode;
    public final int versionCode;
    final List<CapabilityInfoParcelable> zzbsA;

    GetAllCapabilitiesResponse(int n2, int n3, List<CapabilityInfoParcelable> list) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsA = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzah.zza(this, parcel, n2);
    }
}

