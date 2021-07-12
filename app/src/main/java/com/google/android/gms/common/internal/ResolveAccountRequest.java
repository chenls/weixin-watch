/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzy;

public class ResolveAccountRequest
implements SafeParcelable {
    public static final Parcelable.Creator<ResolveAccountRequest> CREATOR = new zzy();
    final int mVersionCode;
    private final Account zzTI;
    private final int zzamq;
    private final GoogleSignInAccount zzamr;

    ResolveAccountRequest(int n2, Account account, int n3, GoogleSignInAccount googleSignInAccount) {
        this.mVersionCode = n2;
        this.zzTI = account;
        this.zzamq = n3;
        this.zzamr = googleSignInAccount;
    }

    public ResolveAccountRequest(Account account, int n2, GoogleSignInAccount googleSignInAccount) {
        this(2, account, n2, googleSignInAccount);
    }

    public int describeContents() {
        return 0;
    }

    public Account getAccount() {
        return this.zzTI;
    }

    public int getSessionId() {
        return this.zzamq;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzy.zza(this, parcel, n2);
    }

    @Nullable
    public GoogleSignInAccount zzqW() {
        return this.zzamr;
    }
}

