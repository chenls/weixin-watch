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
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.internal.zzbc;

public class NodeParcelable
implements SafeParcelable,
Node {
    public static final Parcelable.Creator<NodeParcelable> CREATOR = new zzbc();
    final int mVersionCode;
    private final String zzWQ;
    private final int zzbsY;
    private final boolean zzbsZ;
    private final String zzyv;

    NodeParcelable(int n2, String string2, String string3, int n3, boolean bl2) {
        this.mVersionCode = n2;
        this.zzyv = string2;
        this.zzWQ = string3;
        this.zzbsY = n3;
        this.zzbsZ = bl2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof NodeParcelable)) {
            return false;
        }
        return ((NodeParcelable)object).zzyv.equals(this.zzyv);
    }

    @Override
    public String getDisplayName() {
        return this.zzWQ;
    }

    public int getHopCount() {
        return this.zzbsY;
    }

    @Override
    public String getId() {
        return this.zzyv;
    }

    public int hashCode() {
        return this.zzyv.hashCode();
    }

    @Override
    public boolean isNearby() {
        return this.zzbsZ;
    }

    public String toString() {
        return "Node{" + this.zzWQ + ", id=" + this.zzyv + ", hops=" + this.zzbsY + ", isNearby=" + this.zzbsZ + "}";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzbc.zza(this, parcel, n2);
    }
}

