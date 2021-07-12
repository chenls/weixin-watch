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
import com.google.android.gms.wearable.internal.NodeParcelable;
import com.google.android.gms.wearable.internal.zzat;

public class GetLocalNodeResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetLocalNodeResponse> CREATOR = new zzat();
    public final int statusCode;
    public final int versionCode;
    public final NodeParcelable zzbsL;

    GetLocalNodeResponse(int n2, int n3, NodeParcelable nodeParcelable) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsL = nodeParcelable;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzat.zza(this, parcel, n2);
    }
}

