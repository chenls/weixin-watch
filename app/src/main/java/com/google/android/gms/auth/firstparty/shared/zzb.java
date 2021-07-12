/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.firstparty.shared.FACLConfig;
import com.google.android.gms.auth.firstparty.shared.FACLData;
import com.google.android.gms.common.internal.safeparcel.zza;

public class zzb
implements Parcelable.Creator<FACLData> {
    static void zza(FACLData fACLData, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.zzb.zzav(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, fACLData.version);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, fACLData.zzYs, n2, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, fACLData.zzYt, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, fACLData.zzYu);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, fACLData.zzYv, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzX(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaU(n2);
    }

    public FACLData zzX(Parcel parcel) {
        boolean bl2 = false;
        String string2 = null;
        int n2 = zza.zzau(parcel);
        String string3 = null;
        FACLConfig fACLConfig = null;
        int n3 = 0;
        block7: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block7;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block7;
                }
                case 2: {
                    fACLConfig = zza.zza(parcel, n4, FACLConfig.CREATOR);
                    continue block7;
                }
                case 3: {
                    string3 = zza.zzp(parcel, n4);
                    continue block7;
                }
                case 4: {
                    bl2 = zza.zzc(parcel, n4);
                    continue block7;
                }
                case 5: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new FACLData(n3, fACLConfig, string3, bl2, string2);
    }

    public FACLData[] zzaU(int n2) {
        return new FACLData[n2];
    }
}

