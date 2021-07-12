/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;

public class zzb
implements Parcelable.Creator<Scope> {
    static void zza(Scope scope, Parcel parcel, int n2) {
        n2 = com.google.android.gms.common.internal.safeparcel.zzb.zzav(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, scope.mVersionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, scope.zzpb(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzah(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbx(n2);
    }

    public Scope zzah(Parcel parcel) {
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        String string2 = null;
        block4: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new Scope(n3, string2);
    }

    public Scope[] zzbx(int n2) {
        return new Scope[n2];
    }
}

