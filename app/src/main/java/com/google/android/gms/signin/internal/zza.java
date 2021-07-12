/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.signin.internal.AuthAccountResult;

public class zza
implements Parcelable.Creator<AuthAccountResult> {
    static void zza(AuthAccountResult authAccountResult, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, authAccountResult.mVersionCode);
        zzb.zzc(parcel, 2, authAccountResult.zzFK());
        zzb.zza(parcel, 3, (Parcelable)authAccountResult.zzFL(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzgR(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzjY(n2);
    }

    public AuthAccountResult zzgR(Parcel parcel) {
        int n2 = 0;
        int n3 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        Intent intent = null;
        int n4 = 0;
        block5: while (parcel.dataPosition() < n3) {
            int n5 = com.google.android.gms.common.internal.safeparcel.zza.zzat(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzca(n5)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, n5);
                    continue block5;
                }
                case 1: {
                    n4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n5);
                    continue block5;
                }
                case 2: {
                    n2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n5);
                    continue block5;
                }
                case 3: 
            }
            intent = (Intent)com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, n5, Intent.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new AuthAccountResult(n4, n2, intent);
    }

    public AuthAccountResult[] zzjY(int n2) {
        return new AuthAccountResult[n2];
    }
}

