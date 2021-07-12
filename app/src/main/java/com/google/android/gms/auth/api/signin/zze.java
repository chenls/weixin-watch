/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze
implements Parcelable.Creator<SignInAccount> {
    static void zza(SignInAccount signInAccount, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, signInAccount.versionCode);
        zzb.zza(parcel, 2, signInAccount.zzmT(), false);
        zzb.zza(parcel, 3, signInAccount.getIdToken(), false);
        zzb.zza(parcel, 4, signInAccount.getEmail(), false);
        zzb.zza(parcel, 5, signInAccount.getDisplayName(), false);
        zzb.zza(parcel, 6, (Parcelable)signInAccount.getPhotoUrl(), n2, false);
        zzb.zza(parcel, 7, signInAccount.zzmV(), n2, false);
        zzb.zza(parcel, 8, signInAccount.getUserId(), false);
        zzb.zza(parcel, 9, signInAccount.zzmW(), false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzT(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaO(n2);
    }

    public SignInAccount zzT(Parcel parcel) {
        String string2 = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        String string3 = "";
        GoogleSignInAccount googleSignInAccount = null;
        Uri uri = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        block11: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block11;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block11;
                }
                case 2: {
                    string7 = zza.zzp(parcel, n4);
                    continue block11;
                }
                case 3: {
                    string6 = zza.zzp(parcel, n4);
                    continue block11;
                }
                case 4: {
                    string5 = zza.zzp(parcel, n4);
                    continue block11;
                }
                case 5: {
                    string4 = zza.zzp(parcel, n4);
                    continue block11;
                }
                case 6: {
                    uri = (Uri)zza.zza(parcel, n4, Uri.CREATOR);
                    continue block11;
                }
                case 7: {
                    googleSignInAccount = zza.zza(parcel, n4, GoogleSignInAccount.CREATOR);
                    continue block11;
                }
                case 8: {
                    string3 = zza.zzp(parcel, n4);
                    continue block11;
                }
                case 9: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new SignInAccount(n3, string7, string6, string5, string4, uri, googleSignInAccount, string3, string2);
    }

    public SignInAccount[] zzaO(int n2) {
        return new SignInAccount[n2];
    }
}

