/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.FACLConfig;
import com.google.android.gms.auth.firstparty.shared.zzb;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class FACLData
implements SafeParcelable {
    public static final zzb CREATOR = new zzb();
    final int version;
    FACLConfig zzYs;
    String zzYt;
    boolean zzYu;
    String zzYv;

    FACLData(int n2, FACLConfig fACLConfig, String string2, boolean bl2, String string3) {
        this.version = n2;
        this.zzYs = fACLConfig;
        this.zzYt = string2;
        this.zzYu = bl2;
        this.zzYv = string3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzb.zza(this, parcel, n2);
    }
}

