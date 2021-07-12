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
import com.google.android.gms.wearable.internal.zzal;

public class GetCloudSyncOptInOutDoneResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetCloudSyncOptInOutDoneResponse> CREATOR = new zzal();
    public final int statusCode;
    public final int versionCode;
    public final boolean zzbsD;

    GetCloudSyncOptInOutDoneResponse(int n2, int n3, boolean bl2) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsD = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzal.zza(this, parcel, n2);
    }
}

