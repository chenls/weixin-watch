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
import com.mobvoi.android.wearable.internal.ConnectionConfiguration;

public class GetConfigResponse
implements SafeParcelable {
    public static final Parcelable.Creator<GetConfigResponse> CREATOR = new Parcelable.Creator<GetConfigResponse>(){

        public GetConfigResponse a(Parcel parcel) {
            return new GetConfigResponse(parcel);
        }

        public GetConfigResponse[] a(int n2) {
            return new GetConfigResponse[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };
    private ConnectionConfiguration a;

    public GetConfigResponse(Parcel parcel) {
        this.a = (ConnectionConfiguration)ConnectionConfiguration.CREATOR.createFromParcel(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        this.a.writeToParcel(parcel, n2);
    }
}

