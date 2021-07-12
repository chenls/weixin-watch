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
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

public class zzb
implements Parcelable.Creator<GoogleSignInAccount> {
    static void zza(GoogleSignInAccount googleSignInAccount, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.zzb.zzav(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, googleSignInAccount.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, googleSignInAccount.getId(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, googleSignInAccount.getIdToken(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, googleSignInAccount.getEmail(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, googleSignInAccount.getDisplayName(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable)googleSignInAccount.getPhotoUrl(), n2, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, googleSignInAccount.getServerAuthCode(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, googleSignInAccount.zzmK());
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, googleSignInAccount.zzmL(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 10, googleSignInAccount.zzVs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzR(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaM(n2);
    }

    public GoogleSignInAccount zzR(Parcel parcel) {
        ArrayList<Scope> arrayList = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        long l2 = 0L;
        String string2 = null;
        String string3 = null;
        Uri uri = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        block12: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block12;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block12;
                }
                case 2: {
                    string7 = zza.zzp(parcel, n4);
                    continue block12;
                }
                case 3: {
                    string6 = zza.zzp(parcel, n4);
                    continue block12;
                }
                case 4: {
                    string5 = zza.zzp(parcel, n4);
                    continue block12;
                }
                case 5: {
                    string4 = zza.zzp(parcel, n4);
                    continue block12;
                }
                case 6: {
                    uri = (Uri)zza.zza(parcel, n4, Uri.CREATOR);
                    continue block12;
                }
                case 7: {
                    string3 = zza.zzp(parcel, n4);
                    continue block12;
                }
                case 8: {
                    l2 = zza.zzi(parcel, n4);
                    continue block12;
                }
                case 9: {
                    string2 = zza.zzp(parcel, n4);
                    continue block12;
                }
                case 10: 
            }
            arrayList = zza.zzc(parcel, n4, Scope.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new GoogleSignInAccount(n3, string7, string6, string5, string4, uri, string3, l2, string2, arrayList);
    }

    public GoogleSignInAccount[] zzaM(int n2) {
        return new GoogleSignInAccount[n2];
    }
}

