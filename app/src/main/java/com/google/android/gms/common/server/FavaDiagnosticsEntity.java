/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package com.google.android.gms.common.server;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.zza;

public class FavaDiagnosticsEntity
implements SafeParcelable {
    public static final zza CREATOR = new zza();
    final int mVersionCode;
    public final String zzamD;
    public final int zzamE;

    public FavaDiagnosticsEntity(int n2, String string2, int n3) {
        this.mVersionCode = n2;
        this.zzamD = string2;
        this.zzamE = n3;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zza.zza(this, parcel, n2);
    }
}

