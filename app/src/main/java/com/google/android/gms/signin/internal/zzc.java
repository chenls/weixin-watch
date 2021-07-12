/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.signin.internal.CheckServerAuthResult;
import java.util.ArrayList;

public class zzc
implements Parcelable.Creator<CheckServerAuthResult> {
    static void zza(CheckServerAuthResult checkServerAuthResult, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, checkServerAuthResult.mVersionCode);
        zzb.zza(parcel, 2, checkServerAuthResult.zzbhf);
        zzb.zzc(parcel, 3, checkServerAuthResult.zzbhg, false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzgS(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzjZ(n2);
    }

    public CheckServerAuthResult zzgS(Parcel parcel) {
        boolean bl2 = false;
        int n2 = zza.zzau(parcel);
        ArrayList<Scope> arrayList = null;
        int n3 = 0;
        block5: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block5;
                }
                case 2: {
                    bl2 = zza.zzc(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            arrayList = zza.zzc(parcel, n4, Scope.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new CheckServerAuthResult(n3, bl2, arrayList);
    }

    public CheckServerAuthResult[] zzjZ(int n2) {
        return new CheckServerAuthResult[n2];
    }
}

