/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.FavaDiagnosticsEntity;

public class zza
implements Parcelable.Creator<FavaDiagnosticsEntity> {
    static void zza(FavaDiagnosticsEntity favaDiagnosticsEntity, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, favaDiagnosticsEntity.mVersionCode);
        zzb.zza(parcel, 2, favaDiagnosticsEntity.zzamD, false);
        zzb.zzc(parcel, 3, favaDiagnosticsEntity.zzamE);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaw(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzcc(n2);
    }

    public FavaDiagnosticsEntity zzaw(Parcel parcel) {
        int n2 = 0;
        int n3 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        String string2 = null;
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
                    string2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n5);
                    continue block5;
                }
                case 3: 
            }
            n2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new FavaDiagnosticsEntity(n4, string2, n2);
    }

    public FavaDiagnosticsEntity[] zzcc(int n2) {
        return new FavaDiagnosticsEntity[n2];
    }
}

