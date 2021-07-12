/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.common.internal.safeparcel.zza;

public class zzb
implements Parcelable.Creator<AccountChangeEventsRequest> {
    static void zza(AccountChangeEventsRequest accountChangeEventsRequest, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.zzb.zzav(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, accountChangeEventsRequest.mVersion);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, accountChangeEventsRequest.zzVc);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, accountChangeEventsRequest.zzVa, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable)accountChangeEventsRequest.zzTI, n2, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzA(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzav(n2);
    }

    public AccountChangeEventsRequest zzA(Parcel parcel) {
        Account account = null;
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        String string2 = null;
        int n4 = 0;
        block6: while (parcel.dataPosition() < n3) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block6;
                }
                case 1: {
                    n4 = zza.zzg(parcel, n5);
                    continue block6;
                }
                case 2: {
                    n2 = zza.zzg(parcel, n5);
                    continue block6;
                }
                case 3: {
                    string2 = zza.zzp(parcel, n5);
                    continue block6;
                }
                case 4: 
            }
            account = (Account)zza.zza(parcel, n5, Account.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new AccountChangeEventsRequest(n4, n2, string2, account);
    }

    public AccountChangeEventsRequest[] zzav(int n2) {
        return new AccountChangeEventsRequest[n2];
    }
}

