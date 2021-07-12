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
import com.google.android.gms.wearable.internal.zzan;

public class GetCloudSyncSettingResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetCloudSyncSettingResponse> CREATOR = new zzan();
    public final boolean enabled;
    public final int statusCode;
    public final int versionCode;

    GetCloudSyncSettingResponse(int n2, int n3, boolean bl2) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.enabled = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzan.zza(this, parcel, n2);
    }
}

