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
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.internal.NodeParcelable;
import com.google.android.gms.wearable.internal.zzk;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CapabilityInfoParcelable
implements SafeParcelable,
CapabilityInfo {
    public static final Parcelable.Creator<CapabilityInfoParcelable> CREATOR = new zzk();
    private final String mName;
    final int mVersionCode;
    private Set<Node> zzbrS;
    private final List<NodeParcelable> zzbrV;
    private final Object zzpV = new Object();

    CapabilityInfoParcelable(int n2, String string2, List<NodeParcelable> list) {
        this.mVersionCode = n2;
        this.mName = string2;
        this.zzbrV = list;
        this.zzbrS = null;
        this.zzIH();
    }

    private void zzIH() {
        zzx.zzz(this.mName);
        zzx.zzz(this.zzbrV);
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (CapabilityInfoParcelable)object;
        if (this.mVersionCode != ((CapabilityInfoParcelable)object).mVersionCode) {
            return false;
        }
        if (this.mName != null) {
            if (!this.mName.equals(((CapabilityInfoParcelable)object).mName)) {
                return false;
            }
        } else if (((CapabilityInfoParcelable)object).mName != null) return false;
        if (this.zzbrV != null) {
            if (this.zzbrV.equals(((CapabilityInfoParcelable)object).zzbrV)) return true;
            return false;
        }
        if (((CapabilityInfoParcelable)object).zzbrV == null) return true;
        return false;
    }

    @Override
    public String getName() {
        return this.mName;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Set<Node> getNodes() {
        Object object = this.zzpV;
        synchronized (object) {
            if (this.zzbrS != null) return this.zzbrS;
            this.zzbrS = new HashSet<NodeParcelable>(this.zzbrV);
            return this.zzbrS;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n2 = 0;
        int n3 = this.mVersionCode;
        int n4 = this.mName != null ? this.mName.hashCode() : 0;
        if (this.zzbrV != null) {
            n2 = this.zzbrV.hashCode();
        }
        return (n4 + n3 * 31) * 31 + n2;
    }

    public String toString() {
        return "CapabilityInfo{" + this.mName + ", " + this.zzbrV + "}";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzk.zza(this, parcel, n2);
    }

    public List<NodeParcelable> zzII() {
        return this.zzbrV;
    }
}

