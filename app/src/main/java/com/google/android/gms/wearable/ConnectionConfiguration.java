/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.wearable.zzg;

public class ConnectionConfiguration
implements SafeParcelable {
    public static final Parcelable.Creator<ConnectionConfiguration> CREATOR = new zzg();
    private final String mName;
    final int mVersionCode;
    private boolean zzTb;
    private final int zzabB;
    private final int zzapo;
    private final String zzawc;
    private final boolean zzbqY;
    private String zzbqZ;
    private boolean zzbra;
    private String zzbrb;

    ConnectionConfiguration(int n2, String string2, String string3, int n3, int n4, boolean bl2, boolean bl3, String string4, boolean bl4, String string5) {
        this.mVersionCode = n2;
        this.mName = string2;
        this.zzawc = string3;
        this.zzabB = n3;
        this.zzapo = n4;
        this.zzbqY = bl2;
        this.zzTb = bl3;
        this.zzbqZ = string4;
        this.zzbra = bl4;
        this.zzbrb = string5;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof ConnectionConfiguration)) break block2;
                object = (ConnectionConfiguration)object;
                if (zzw.equal(this.mVersionCode, ((ConnectionConfiguration)object).mVersionCode) && zzw.equal(this.mName, ((ConnectionConfiguration)object).mName) && zzw.equal(this.zzawc, ((ConnectionConfiguration)object).zzawc) && zzw.equal(this.zzabB, ((ConnectionConfiguration)object).zzabB) && zzw.equal(this.zzapo, ((ConnectionConfiguration)object).zzapo) && zzw.equal(this.zzbqY, ((ConnectionConfiguration)object).zzbqY) && zzw.equal(this.zzbra, ((ConnectionConfiguration)object).zzbra)) break block3;
            }
            return false;
        }
        return true;
    }

    public String getAddress() {
        return this.zzawc;
    }

    public String getName() {
        return this.mName;
    }

    public String getNodeId() {
        return this.zzbrb;
    }

    public int getRole() {
        return this.zzapo;
    }

    public int getType() {
        return this.zzabB;
    }

    public int hashCode() {
        return zzw.hashCode(this.mVersionCode, this.mName, this.zzawc, this.zzabB, this.zzapo, this.zzbqY, this.zzbra);
    }

    public boolean isConnected() {
        return this.zzTb;
    }

    public boolean isEnabled() {
        return this.zzbqY;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ConnectionConfiguration[ ");
        stringBuilder.append("mName=" + this.mName);
        stringBuilder.append(", mAddress=" + this.zzawc);
        stringBuilder.append(", mType=" + this.zzabB);
        stringBuilder.append(", mRole=" + this.zzapo);
        stringBuilder.append(", mEnabled=" + this.zzbqY);
        stringBuilder.append(", mIsConnected=" + this.zzTb);
        stringBuilder.append(", mPeerNodeId=" + this.zzbqZ);
        stringBuilder.append(", mBtlePriority=" + this.zzbra);
        stringBuilder.append(", mNodeId=" + this.zzbrb);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzg.zza(this, parcel, n2);
    }

    public String zzIt() {
        return this.zzbqZ;
    }

    public boolean zzIu() {
        return this.zzbra;
    }
}

