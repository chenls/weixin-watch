/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;

public class StorageInfoResponse
implements SafeParcelable {
    public static final Parcelable.Creator<StorageInfoResponse> CREATOR = new Parcelable.Creator<StorageInfoResponse>(){

        public StorageInfoResponse a(Parcel parcel) {
            return null;
        }

        public StorageInfoResponse[] a(int n2) {
            return null;
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
    }
}

