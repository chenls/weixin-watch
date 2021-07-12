/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzc
implements Parcelable.Creator<GoogleSignInOptions> {
    static void zza(GoogleSignInOptions googleSignInOptions, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, googleSignInOptions.versionCode);
        zzb.zzc(parcel, 2, googleSignInOptions.zzmN(), false);
        zzb.zza(parcel, 3, (Parcelable)googleSignInOptions.getAccount(), n2, false);
        zzb.zza(parcel, 4, googleSignInOptions.zzmO());
        zzb.zza(parcel, 5, googleSignInOptions.zzmP());
        zzb.zza(parcel, 6, googleSignInOptions.zzmQ());
        zzb.zza(parcel, 7, googleSignInOptions.zzmR(), false);
        zzb.zza(parcel, 8, googleSignInOptions.zzmS(), false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzS(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaN(n2);
    }

    public GoogleSignInOptions zzS(Parcel parcel) {
        String string2 = null;
        boolean bl2 = false;
        int n2 = zza.zzau(parcel);
        String string3 = null;
        boolean bl3 = false;
        boolean bl4 = false;
        Account account = null;
        ArrayList<Scope> arrayList = null;
        int n3 = 0;
        block10: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block10;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block10;
                }
                case 2: {
                    arrayList = zza.zzc(parcel, n4, Scope.CREATOR);
                    continue block10;
                }
                case 3: {
                    account = (Account)zza.zza(parcel, n4, Account.CREATOR);
                    continue block10;
                }
                case 4: {
                    bl4 = zza.zzc(parcel, n4);
                    continue block10;
                }
                case 5: {
                    bl3 = zza.zzc(parcel, n4);
                    continue block10;
                }
                case 6: {
                    bl2 = zza.zzc(parcel, n4);
                    continue block10;
                }
                case 7: {
                    string3 = zza.zzp(parcel, n4);
                    continue block10;
                }
                case 8: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new GoogleSignInOptions(n3, arrayList, account, bl4, bl3, bl2, string3, string2);
    }

    public GoogleSignInOptions[] zzaN(int n2) {
        return new GoogleSignInOptions[n2];
    }
}

