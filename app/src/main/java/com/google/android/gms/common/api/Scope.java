/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.zzb;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public final class Scope
implements SafeParcelable {
    public static final Parcelable.Creator<Scope> CREATOR = new zzb();
    final int mVersionCode;
    private final String zzagB;

    Scope(int n2, String string2) {
        zzx.zzh(string2, "scopeUri must not be null or empty");
        this.mVersionCode = n2;
        this.zzagB = string2;
    }

    public Scope(String string2) {
        this(1, string2);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Scope)) {
            return false;
        }
        return this.zzagB.equals(((Scope)object).zzagB);
    }

    public int hashCode() {
        return this.zzagB.hashCode();
    }

    public String toString() {
        return this.zzagB;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzb.zza(this, parcel, n2);
    }

    public String zzpb() {
        return this.zzagB;
    }
}

