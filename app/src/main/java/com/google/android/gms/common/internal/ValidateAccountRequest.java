/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzae;

@Deprecated
public class ValidateAccountRequest
implements SafeParcelable {
    public static final Parcelable.Creator<ValidateAccountRequest> CREATOR = new zzae();
    final int mVersionCode;
    private final String zzVO;
    private final Scope[] zzafT;
    final IBinder zzakA;
    private final int zzamy;
    private final Bundle zzamz;

    ValidateAccountRequest(int n2, int n3, IBinder iBinder, Scope[] scopeArray, Bundle bundle, String string2) {
        this.mVersionCode = n2;
        this.zzamy = n3;
        this.zzakA = iBinder;
        this.zzafT = scopeArray;
        this.zzamz = bundle;
        this.zzVO = string2;
    }

    public int describeContents() {
        return 0;
    }

    public String getCallingPackage() {
        return this.zzVO;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzae.zza(this, parcel, n2);
    }

    public Scope[] zzrd() {
        return this.zzafT;
    }

    public int zzre() {
        return this.zzamy;
    }

    public Bundle zzrf() {
        return this.zzamz;
    }
}

