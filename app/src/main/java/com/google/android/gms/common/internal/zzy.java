/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzy
implements Parcelable.Creator<ResolveAccountRequest> {
    static void zza(ResolveAccountRequest resolveAccountRequest, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, resolveAccountRequest.mVersionCode);
        zzb.zza(parcel, 2, (Parcelable)resolveAccountRequest.getAccount(), n2, false);
        zzb.zzc(parcel, 3, resolveAccountRequest.getSessionId());
        zzb.zza(parcel, 4, resolveAccountRequest.zzqW(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzap(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbW(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ResolveAccountRequest zzap(Parcel parcel) {
        GoogleSignInAccount googleSignInAccount = null;
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        Account account = null;
        int n4 = 0;
        while (parcel.dataPosition() < n3) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    n5 = n2;
                    n2 = n4;
                    n4 = n5;
                    break;
                }
                case 1: {
                    n5 = zza.zzg(parcel, n5);
                    n4 = n2;
                    n2 = n5;
                    break;
                }
                case 2: {
                    account = (Account)zza.zza(parcel, n5, Account.CREATOR);
                    n5 = n4;
                    n4 = n2;
                    n2 = n5;
                    break;
                }
                case 3: {
                    n5 = zza.zzg(parcel, n5);
                    n2 = n4;
                    n4 = n5;
                    break;
                }
                case 4: {
                    googleSignInAccount = zza.zza(parcel, n5, GoogleSignInAccount.CREATOR);
                    n5 = n4;
                    n4 = n2;
                    n2 = n5;
                }
            }
            n5 = n2;
            n2 = n4;
            n4 = n5;
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new ResolveAccountRequest(n4, account, n2, googleSignInAccount);
    }

    public ResolveAccountRequest[] zzbW(int n2) {
        return new ResolveAccountRequest[n2];
    }
}

