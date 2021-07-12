/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import java.util.Locale;
import mobvoiapi.bh;
import mobvoiapi.bl;

public final class AddListenerRequest
implements SafeParcelable {
    public static final Parcelable.Creator<AddListenerRequest> CREATOR = new Parcelable.Creator<AddListenerRequest>(){

        public AddListenerRequest a(Parcel parcel) {
            return new AddListenerRequest(parcel);
        }

        public AddListenerRequest[] a(int n2) {
            return new AddListenerRequest[n2];
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
    public final IntentFilter[] c;

    /*
     * Enabled aggressive block sorting
     */
    public AddListenerRequest(Parcel parcel) {
        Object var4_2 = null;
        this.a = parcel.readInt();
        Object object = parcel.readStrongBinder();
        object = object != null ? bh.a.asInterface((IBinder)object) : null;
        this.b = object;
        int n2 = parcel.readInt();
        object = var4_2;
        if (n2 > 0) {
            object = new IntentFilter[n2];
            parcel.readTypedArray(object, IntentFilter.CREATOR);
        }
        this.c = object;
    }

    public AddListenerRequest(bl bl2) {
        this.a = 1;
        this.b = bl2;
        this.c = bl2.a();
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
        return String.format(Locale.US, "AddListenerRequest[mRequestId=%d, mListener=%s]", this.a, this.b);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.a);
        parcel.writeStrongBinder(this.a());
        int n3 = this.c == null ? 0 : this.c.length;
        parcel.writeInt(n3);
        parcel.writeParcelableArray((Parcelable[])this.c, n2);
    }
}

