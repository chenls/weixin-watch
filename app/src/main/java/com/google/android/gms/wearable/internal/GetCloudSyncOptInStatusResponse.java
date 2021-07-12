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
import com.google.android.gms.wearable.internal.zzam;

public class GetCloudSyncOptInStatusResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetCloudSyncOptInStatusResponse> CREATOR = new zzam();
    public final int statusCode;
    public final int versionCode;
    public final boolean zzbsE;
    public final boolean zzbsF;

    GetCloudSyncOptInStatusResponse(int n2, int n3, boolean bl2, boolean bl3) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsE = bl2;
        this.zzbsF = bl3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzam.zza(this, parcel, n2);
    }
}

