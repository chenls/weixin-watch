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
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
implements Parcelable.Creator<FACLConfig> {
    static void zza(FACLConfig fACLConfig, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, fACLConfig.version);
        zzb.zza(parcel, 2, fACLConfig.zzYm);
        zzb.zza(parcel, 3, fACLConfig.zzYn, false);
        zzb.zza(parcel, 4, fACLConfig.zzYo);
        zzb.zza(parcel, 5, fACLConfig.zzYp);
        zzb.zza(parcel, 6, fACLConfig.zzYq);
        zzb.zza(parcel, 7, fACLConfig.zzYr);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzW(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzaT(n2);
    }

    public FACLConfig zzW(Parcel parcel) {
        boolean bl2 = false;
        int n2 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        String string2 = null;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        int n3 = 0;
        block9: while (parcel.dataPosition() < n2) {
            int n4 = com.google.android.gms.common.internal.safeparcel.zza.zzat(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzca(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, n4);
                    continue block9;
                }
                case 1: {
                    n3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n4);
                    continue block9;
                }
                case 2: {
                    bl6 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, n4);
                    continue block9;
                }
                case 3: {
                    string2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n4);
                    continue block9;
                }
                case 4: {
                    bl5 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, n4);
                    continue block9;
                }
                case 5: {
                    bl4 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, n4);
                    continue block9;
                }
                case 6: {
                    bl3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, n4);
                    continue block9;
                }
                case 7: 
            }
            bl2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new FACLConfig(n3, bl6, string2, bl5, bl4, bl3, bl2);
    }

    public FACLConfig[] zzaT(int n2) {
        return new FACLConfig[n2];
    }
}

