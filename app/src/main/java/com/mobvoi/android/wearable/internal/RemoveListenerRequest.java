/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import java.util.Locale;
import mobvoiapi.bh;
import mobvoiapi.bl;

public final class RemoveListenerRequest
implements SafeParcelable {
    public static final Parcelable.Creator<RemoveListenerRequest> CREATOR = new Parcelable.Creator<RemoveListenerRequest>(){

        public RemoveListenerRequest a(Parcel parcel) {
            return new RemoveListenerRequest(parcel);
        }

        public RemoveListenerRequest[] a(int n2) {
            return new RemoveListenerRequest[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    };
    public final int a;
    public final bh b;

    public RemoveListenerRequest(Parcel object) {
        this.a = object.readInt();
        IBinder iBinder = object.readStrongBinder();
        object = null;
        if (iBinder != null) {
            object = bh.a.asInterface(iBinder);
        }
        this.b = object;
    }

    public RemoveListenerRequest(bl bl2) {
        this.a = 1;
        this.b = bl2;
    }

    public IBinder a() {
        if (this.b != null) {
            return this.b.asBinder();
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return String.format(Locale.US, "RemoveListenerRequest[requestId=%d, listener=%s]", this.a, this.b);
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.a);
        parcel.writeStrongBinder(this.a());
    }
}

