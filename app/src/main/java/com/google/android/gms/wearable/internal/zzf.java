/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.internal.AmsEntityUpdateParcelable;

public class zzf
implements Parcelable.Creator<AmsEntityUpdateParcelable> {
    static void zza(AmsEntityUpdateParcelable amsEntityUpdateParcelable, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, amsEntityUpdateParcelable.mVersionCode);
        zzb.zza(parcel, 2, amsEntityUpdateParcelable.zzIz());
        zzb.zza(parcel, 3, amsEntityUpdateParcelable.zzIA());
        zzb.zza(parcel, 4, amsEntityUpdateParcelable.getValue(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzic(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlD(n2);
    }

    public AmsEntityUpdateParcelable zzic(Parcel parcel) {
        byte by2 = 0;
        int n2 = zza.zzau(parcel);
        String string2 = null;
        byte by3 = 0;
        int n3 = 0;
        block6: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block6;
                }
                case 2: {
                    by3 = zza.zze(parcel, n4);
                    continue block6;
                }
                case 3: {
                    by2 = zza.zze(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new AmsEntityUpdateParcelable(n3, by3, by2, string2);
    }

    public AmsEntityUpdateParcelable[] zzlD(int n2) {
        return new AmsEntityUpdateParcelable[n2];
    }
}

