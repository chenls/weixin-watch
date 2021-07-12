/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.auth.zzb;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class AccountChangeEventsRequest
implements SafeParcelable {
    public static final Parcelable.Creator<AccountChangeEventsRequest> CREATOR = new zzb();
    final int mVersion;
    Account zzTI;
    @Deprecated
    String zzVa;
    int zzVc;

    public AccountChangeEventsRequest() {
        this.mVersion = 1;
    }

    AccountChangeEventsRequest(int n2, int n3, String string2, Account account) {
        this.mVersion = n2;
        this.zzVc = n3;
        this.zzVa = string2;
        if (account == null && !TextUtils.isEmpty((CharSequence)string2)) {
            this.zzTI = new Account(string2, "com.google");
            return;
        }
        this.zzTI = account;
    }

    public int describeContents() {
        return 0;
    }

    public Account getAccount() {
        return this.zzTI;
    }

    public String getAccountName() {
        return this.zzVa;
    }

    public int getEventIndex() {
        return this.zzVc;
    }

    public AccountChangeEventsRequest setAccount(Account account) {
        this.zzTI = account;
        return this;
    }

    public AccountChangeEventsRequest setAccountName(String string2) {
        this.zzVa = string2;
        return this;
    }

    public AccountChangeEventsRequest setEventIndex(int n2) {
        this.zzVc = n2;
        return this;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzb.zza(this, parcel, n2);
    }
}

