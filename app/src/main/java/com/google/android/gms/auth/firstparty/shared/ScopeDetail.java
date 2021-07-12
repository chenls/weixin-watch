/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import com.google.android.gms.auth.firstparty.shared.FACLData;
import com.google.android.gms.auth.firstparty.shared.zzc;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

public class ScopeDetail
implements SafeParcelable {
    public static final zzc CREATOR = new zzc();
    String description;
    final int version;
    List<String> zzYA;
    public FACLData zzYB;
    String zzYw;
    String zzYx;
    String zzYy;
    String zzYz;

    ScopeDetail(int n2, String string2, String string3, String string4, String string5, String string6, List<String> list, FACLData fACLData) {
        this.version = n2;
        this.description = string2;
        this.zzYw = string3;
        this.zzYx = string4;
        this.zzYy = string5;
        this.zzYz = string6;
        this.zzYA = list;
        this.zzYB = fACLData;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzc.zza(this, parcel, n2);
    }
}

