/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.signin.internal.zzf;

public class RecordConsentRequest
implements SafeParcelable {
    public static final Parcelable.Creator<RecordConsentRequest> CREATOR = new zzf();
    final int mVersionCode;
    private final Account zzTI;
    private final String zzXd;
    private final Scope[] zzbhh;

    RecordConsentRequest(int n2, Account account, Scope[] scopeArray, String string2) {
        this.mVersionCode = n2;
        this.zzTI = account;
        this.zzbhh = scopeArray;
        this.zzXd = string2;
    }

    public int describeContents() {
        return 0;
    }

    public Account getAccount() {
        return this.zzTI;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzf.zza(this, parcel, n2);
    }

    public Scope[] zzFM() {
        return this.zzbhh;
    }

    public String zzmR() {
        return this.zzXd;
    }
}

