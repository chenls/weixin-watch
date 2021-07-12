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
import com.google.android.gms.wearable.internal.zzbi;

public class SendMessageResponse
implements SafeParcelable {
    public static final Parcelable.Creator<SendMessageResponse> CREATOR = new zzbi();
    public final int statusCode;
    public final int versionCode;
    public final int zzaNj;

    SendMessageResponse(int n2, int n3, int n4) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzaNj = n4;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzbi.zza(this, parcel, n2);
    }
}

