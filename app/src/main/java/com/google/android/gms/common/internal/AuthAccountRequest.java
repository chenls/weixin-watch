/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzc;

public class AuthAccountRequest
implements SafeParcelable {
    public static final Parcelable.Creator<AuthAccountRequest> CREATOR = new zzc();
    final int mVersionCode;
    final Scope[] zzafT;
    final IBinder zzakA;
    Integer zzakB;
    Integer zzakC;

    AuthAccountRequest(int n2, IBinder iBinder, Scope[] scopeArray, Integer n3, Integer n4) {
        this.mVersionCode = n2;
        this.zzakA = iBinder;
        this.zzafT = scopeArray;
        this.zzakB = n3;
        this.zzakC = n4;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzc.zza(this, parcel, n2);
    }
}

