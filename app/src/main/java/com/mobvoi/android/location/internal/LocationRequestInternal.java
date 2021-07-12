/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.mobvoi.android.location.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.mobvoi.android.common.internal.safeparcel.SafeParcelable;
import com.mobvoi.android.location.LocationRequest;

public class LocationRequestInternal
implements SafeParcelable {
    public static final Parcelable.Creator<LocationRequestInternal> CREATOR = new a();
    private final int a;
    private int b;
    private long c;
    private long d;
    private long e;
    private int f;
    private float g;

    public LocationRequestInternal(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readLong();
        this.d = parcel.readLong();
        this.e = parcel.readLong();
        this.f = parcel.readInt();
        this.g = parcel.readFloat();
    }

    public LocationRequestInternal(LocationRequest locationRequest) {
        this.a = locationRequest.getVersionCode();
        this.b = locationRequest.getPriority();
        this.c = locationRequest.getInterval();
        this.d = locationRequest.getFastestInterval();
        this.e = locationRequest.getExpirationTime();
        this.f = locationRequest.getNumUpdates();
        this.g = locationRequest.getSmallestDisplacement();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeLong(this.c);
        parcel.writeLong(this.d);
        parcel.writeLong(this.e);
        parcel.writeInt(this.f);
        parcel.writeFloat(this.g);
    }

    public static class a
    implements Parcelable.Creator<LocationRequestInternal> {
        public LocationRequestInternal a(Parcel parcel) {
            return new LocationRequestInternal(parcel);
        }

        public LocationRequestInternal[] a(int n2) {
            return new LocationRequestInternal[n2];
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.a(parcel);
        }

        public /* synthetic */ Object[] newArray(int n2) {
            return this.a(n2);
        }
    }
}

