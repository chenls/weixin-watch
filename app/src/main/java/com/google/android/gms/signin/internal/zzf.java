/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.signin.internal.RecordConsentRequest;

public class zzf
implements Parcelable.Creator<RecordConsentRequest> {
    static void zza(RecordConsentRequest recordConsentRequest, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, recordConsentRequest.mVersionCode);
        zzb.zza(parcel, 2, (Parcelable)recordConsentRequest.getAccount(), n2, false);
        zzb.zza((Parcel)parcel, (int)3, (Parcelable[])recordConsentRequest.zzFM(), (int)n2, (boolean)false);
        zzb.zza(parcel, 4, recordConsentRequest.zzmR(), false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzgT(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzkb(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public RecordConsentRequest zzgT(Parcel parcel) {
        String string2 = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        Scope[] scopeArray = null;
        Scope[] scopeArray2 = null;
        while (parcel.dataPosition() < n2) {
            Scope[] scopeArray3;
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    scopeArray3 = scopeArray;
                    scopeArray = scopeArray2;
                    scopeArray2 = scopeArray3;
                    break;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    scopeArray3 = scopeArray2;
                    scopeArray2 = scopeArray;
                    scopeArray = scopeArray3;
                    break;
                }
                case 2: {
                    scopeArray3 = (Scope[])zza.zza(parcel, n4, Account.CREATOR);
                    scopeArray2 = scopeArray;
                    scopeArray = scopeArray3;
                    break;
                }
                case 3: {
                    scopeArray3 = zza.zzb(parcel, n4, Scope.CREATOR);
                    scopeArray = scopeArray2;
                    scopeArray2 = scopeArray3;
                    break;
                }
                case 4: {
                    string2 = zza.zzp(parcel, n4);
                    scopeArray3 = scopeArray2;
                    scopeArray2 = scopeArray;
                    scopeArray = scopeArray3;
                }
            }
            scopeArray3 = scopeArray;
            scopeArray = scopeArray2;
            scopeArray2 = scopeArray3;
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new RecordConsentRequest(n3, (Account)scopeArray2, scopeArray, string2);
    }

    public RecordConsentRequest[] zzkb(int n2) {
        return new RecordConsentRequest[n2];
    }
}

