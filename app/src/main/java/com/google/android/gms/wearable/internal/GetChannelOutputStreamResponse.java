/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.internal.zzak;

public class GetChannelOutputStreamResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetChannelOutputStreamResponse> CREATOR = new zzak();
    public final int statusCode;
    public final int versionCode;
    public final ParcelFileDescriptor zzbsC;

    GetChannelOutputStreamResponse(int n2, int n3, ParcelFileDescriptor parcelFileDescriptor) {
        this.versionCode = n2;
        this.statusCode = n3;
        this.zzbsC = parcelFileDescriptor;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzak.zza(this, parcel, n2);
    }
}

