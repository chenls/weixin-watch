/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.SignInButtonConfig;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzaa
implements Parcelable.Creator<SignInButtonConfig> {
    static void zza(SignInButtonConfig signInButtonConfig, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, signInButtonConfig.mVersionCode);
        zzb.zzc(parcel, 2, signInButtonConfig.zzrb());
        zzb.zzc(parcel, 3, signInButtonConfig.zzrc());
        zzb.zza((Parcel)parcel, (int)4, (Parcelable[])signInButtonConfig.zzrd(), (int)n2, (boolean)false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzar(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbY(n2);
    }

    public SignInButtonConfig zzar(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        Scope[] scopeArray = null;
        int n4 = 0;
        int n5 = 0;
        block6: while (parcel.dataPosition() < n3) {
            int n6 = zza.zzat(parcel);
            switch (zza.zzca(n6)) {
                default: {
                    zza.zzb(parcel, n6);
                    continue block6;
                }
                case 1: {
                    n5 = zza.zzg(parcel, n6);
                    continue block6;
                }
                case 2: {
                    n4 = zza.zzg(parcel, n6);
                    continue block6;
                }
                case 3: {
                    n2 = zza.zzg(parcel, n6);
                    continue block6;
                }
                case 4: 
            }
            scopeArray = zza.zzb(parcel, n6, Scope.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new SignInButtonConfig(n5, n4, n2, scopeArray);
    }

    public SignInButtonConfig[] zzbY(int n2) {
        return new SignInButtonConfig[n2];
    }
}

