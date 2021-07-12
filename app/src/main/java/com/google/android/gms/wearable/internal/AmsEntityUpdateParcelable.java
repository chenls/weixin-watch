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
import com.google.android.gms.wearable.internal.zzf;
import com.google.android.gms.wearable.zzb;

public class AmsEntityUpdateParcelable
implements SafeParcelable,
zzb {
    public static final Parcelable.Creator<AmsEntityUpdateParcelable> CREATOR = new zzf();
    private final String mValue;
    final int mVersionCode;
    private byte zzbrF;
    private final byte zzbrG;

    AmsEntityUpdateParcelable(int n2, byte by2, byte by3, String string2) {
        this.zzbrF = by2;
        this.mVersionCode = n2;
        this.zzbrG = by3;
        this.mValue = string2;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block8: {
            block7: {
                if (this == object) break block7;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (AmsEntityUpdateParcelable)object;
                if (this.zzbrF != ((AmsEntityUpdateParcelable)object).zzbrF) {
                    return false;
                }
                if (this.mVersionCode != ((AmsEntityUpdateParcelable)object).mVersionCode) {
                    return false;
                }
                if (this.zzbrG != ((AmsEntityUpdateParcelable)object).zzbrG) {
                    return false;
                }
                if (!this.mValue.equals(((AmsEntityUpdateParcelable)object).mValue)) break block8;
            }
            return true;
        }
        return false;
    }

    public String getValue() {
        return this.mValue;
    }

    public int hashCode() {
        return ((this.mVersionCode * 31 + this.zzbrF) * 31 + this.zzbrG) * 31 + this.mValue.hashCode();
    }

    public String toString() {
        return "AmsEntityUpdateParcelable{mVersionCode=" + this.mVersionCode + ", mEntityId=" + this.zzbrF + ", mAttributeId=" + this.zzbrG + ", mValue='" + this.mValue + '\'' + '}';
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzf.zza(this, parcel, n2);
    }

    public byte zzIA() {
        return this.zzbrG;
    }

    public byte zzIz() {
        return this.zzbrF;
    }
}

