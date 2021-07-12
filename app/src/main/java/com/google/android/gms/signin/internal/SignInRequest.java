/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.signin.internal.zzi;

public class SignInRequest
implements SafeParcelable {
    public static final Parcelable.Creator<SignInRequest> CREATOR = new zzi();
    final int mVersionCode;
    final ResolveAccountRequest zzbhj;

    SignInRequest(int n2, ResolveAccountRequest resolveAccountRequest) {
        this.mVersionCode = n2;
        this.zzbhj = resolveAccountRequest;
    }

    public SignInRequest(ResolveAccountRequest resolveAccountRequest) {
        this(1, resolveAccountRequest);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzi.zza(this, parcel, n2);
    }

    public ResolveAccountRequest zzFO() {
        return this.zzbhj;
    }
}

