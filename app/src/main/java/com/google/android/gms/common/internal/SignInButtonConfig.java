/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzaa;

public class SignInButtonConfig
implements SafeParcelable {
    public static final Parcelable.Creator<SignInButtonConfig> CREATOR = new zzaa();
    final int mVersionCode;
    private final Scope[] zzafT;
    private final int zzamu;
    private final int zzamv;

    SignInButtonConfig(int n2, int n3, int n4, Scope[] scopeArray) {
        this.mVersionCode = n2;
        this.zzamu = n3;
        this.zzamv = n4;
        this.zzafT = scopeArray;
    }

    public SignInButtonConfig(int n2, int n3, Scope[] scopeArray) {
        this(1, n2, n3, scopeArray);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzaa.zza(this, parcel, n2);
    }

    public int zzrb() {
        return this.zzamu;
    }

    public int zzrc() {
        return this.zzamv;
    }

    public Scope[] zzrd() {
        return this.zzafT;
    }
}

