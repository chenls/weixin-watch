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
import com.google.android.gms.wearable.internal.zzao;

@Deprecated
public class GetConfigResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetConfigResponse> CREATOR = new zzao();
    public final int statusCode;
    public final int versionCode;
    public final ConnectionConfiguration zzbsG;

    GetConfigResponse(int n2, int n3, ConnectionConfiguration connectionConfiguration) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsG = connectionConfiguration;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzao.zza(this, parcel, n2);
    }
}

