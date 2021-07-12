/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.zza;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;

public class AccountChangeEvent
implements SafeParcelable {
    public static final Parcelable.Creator<AccountChangeEvent> CREATOR = new zza();
    final int mVersion;
    final long zzUZ;
    final String zzVa;
    final int zzVb;
    final int zzVc;
    final String zzVd;

    AccountChangeEvent(int n2, long l2, String string2, int n3, int n4, String string3) {
        this.mVersion = n2;
        this.zzUZ = l2;
        this.zzVa = zzx.zzz(string2);
        this.zzVb = n3;
        this.zzVc = n4;
        this.zzVd = string3;
    }

    public AccountChangeEvent(long l2, String string2, int n2, int n3, String string3) {
        this.mVersion = 1;
        this.zzUZ = l2;
        this.zzVa = zzx.zzz(string2);
        this.zzVb = n2;
        this.zzVc = n3;
        this.zzVd = string3;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof AccountChangeEvent)) {
                    return false;
                }
                object = (AccountChangeEvent)object;
                if (this.mVersion != ((AccountChangeEvent)object).mVersion || this.zzUZ != ((AccountChangeEvent)object).zzUZ || !zzw.equal(this.zzVa, ((AccountChangeEvent)object).zzVa) || this.zzVb != ((AccountChangeEvent)object).zzVb || this.zzVc != ((AccountChangeEvent)object).zzVc || !zzw.equal(this.zzVd, ((AccountChangeEvent)object).zzVd)) break block5;
            }
            return true;
        }
        return false;
    }

    public String getAccountName() {
        return this.zzVa;
    }

    public String getChangeData() {
        return this.zzVd;
    }

    public int getChangeType() {
        return this.zzVb;
    }

    public int getEventIndex() {
        return this.zzVc;
    }

    public int hashCode() {
        return zzw.hashCode(this.mVersion, this.zzUZ, this.zzVa, this.zzVb, this.zzVc, this.zzVd);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        String string2 = "UNKNOWN";
        switch (this.zzVb) {
            case 1: {
                string2 = "ADDED";
                return "AccountChangeEvent {accountName = " + this.zzVa + ", changeType = " + string2 + ", changeData = " + this.zzVd + ", eventIndex = " + this.zzVc + "}";
            }
            case 2: {
                string2 = "REMOVED";
                return "AccountChangeEvent {accountName = " + this.zzVa + ", changeType = " + string2 + ", changeData = " + this.zzVd + ", eventIndex = " + this.zzVc + "}";
            }
            case 4: {
                string2 = "RENAMED_TO";
                return "AccountChangeEvent {accountName = " + this.zzVa + ", changeType = " + string2 + ", changeData = " + this.zzVd + ", eventIndex = " + this.zzVc + "}";
            }
            case 3: {
                string2 = "RENAMED_FROM";
                return "AccountChangeEvent {accountName = " + this.zzVa + ", changeType = " + string2 + ", changeData = " + this.zzVd + ", eventIndex = " + this.zzVc + "}";
            }
        }
        return "AccountChangeEvent {accountName = " + this.zzVa + ", changeType = " + string2 + ", changeData = " + this.zzVd + ", eventIndex = " + this.zzVc + "}";
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zza.zza(this, parcel, n2);
    }
}

