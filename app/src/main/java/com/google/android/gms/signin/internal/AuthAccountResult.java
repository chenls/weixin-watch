/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.signin.internal.zza;

public class AuthAccountResult
implements Result,
SafeParcelable {
    public static final Parcelable.Creator<AuthAccountResult> CREATOR = new zza();
    final int mVersionCode;
    private int zzbhd;
    private Intent zzbhe;

    public AuthAccountResult() {
        this(0, null);
    }

    AuthAccountResult(int n2, int n3, Intent intent) {
        this.mVersionCode = n2;
        this.zzbhd = n3;
        this.zzbhe = intent;
    }

    public AuthAccountResult(int n2, Intent intent) {
        this(2, n2, intent);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public Status getStatus() {
        if (this.zzbhd == 0) {
            return Status.zzagC;
        }
        return Status.zzagG;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zza.zza(this, parcel, n2);
    }

    public int zzFK() {
        return this.zzbhd;
    }

    public Intent zzFL() {
        return this.zzbhe;
    }
}

