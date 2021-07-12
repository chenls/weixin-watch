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

public class SendMessageResponse
implements SafeParcelable {
    public static final Parcelable.Creator<SendMessageResponse> CREATOR = new Parcelable.Creator<SendMessageResponse>(){

        public SendMessageResponse a(Parcel parcel) {
            return new SendMessageResponse(parcel);
        }

        public SendMessageResponse[] a(int n2) {
            return new SendMessageResponse[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };
    public int a;
    public int b;
    public int c;

    public SendMessageResponse(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
    }
}

