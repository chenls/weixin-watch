/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.EmailSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.SignInConfiguration;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzp
implements Parcelable.Creator<SignInConfiguration> {
    static void zza(SignInConfiguration signInConfiguration, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, signInConfiguration.versionCode);
        zzb.zza(parcel, 2, signInConfiguration.zznk(), false);
        zzb.zza(parcel, 3, signInConfiguration.zzmR(), false);
        zzb.zza(parcel, 4, signInConfiguration.zznl(), n2, false);
        zzb.zza(parcel, 5, signInConfiguration.zznm(), n2, false);
        zzb.zza(parcel, 7, signInConfiguration.zznn(), false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzV(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaQ(n2);
    }

    public SignInConfiguration zzV(Parcel parcel) {
        String string2 = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        GoogleSignInOptions googleSignInOptions = null;
        EmailSignInOptions emailSignInOptions = null;
        String string3 = null;
        String string4 = null;
        block8: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block8;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block8;
                }
                case 2: {
                    string4 = zza.zzp(parcel, n4);
                    continue block8;
                }
                case 3: {
                    string3 = zza.zzp(parcel, n4);
                    continue block8;
                }
                case 4: {
                    emailSignInOptions = zza.zza(parcel, n4, EmailSignInOptions.CREATOR);
                    continue block8;
                }
                case 5: {
                    googleSignInOptions = zza.zza(parcel, n4, GoogleSignInOptions.CREATOR);
                    continue block8;
                }
                case 7: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new SignInConfiguration(n3, string4, string3, emailSignInOptions, googleSignInOptions, string2);
    }

    public SignInConfiguration[] zzaQ(int n2) {
        return new SignInConfiguration[n2];
    }
}

