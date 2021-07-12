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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.signin.internal.zzj;

public class SignInResponse
implements SafeParcelable {
    public static final Parcelable.Creator<SignInResponse> CREATOR = new zzj();
    final int mVersionCode;
    private final ConnectionResult zzams;
    private final ResolveAccountResponse zzbhk;

    public SignInResponse(int n2) {
        this(new ConnectionResult(n2, null), null);
    }

    SignInResponse(int n2, ConnectionResult connectionResult, ResolveAccountResponse resolveAccountResponse) {
        this.mVersionCode = n2;
        this.zzams = connectionResult;
        this.zzbhk = resolveAccountResponse;
    }

    public SignInResponse(ConnectionResult connectionResult, ResolveAccountResponse resolveAccountResponse) {
        this(1, connectionResult, resolveAccountResponse);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzj.zza(this, parcel, n2);
    }

    public ResolveAccountResponse zzFP() {
        return this.zzbhk;
    }

    public ConnectionResult zzqY() {
        return this.zzams;
    }
}

