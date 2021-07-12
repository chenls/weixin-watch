/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.converter.StringToIntConverter;

public class zzc
implements Parcelable.Creator<StringToIntConverter.Entry> {
    static void zza(StringToIntConverter.Entry entry, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, entry.versionCode);
        zzb.zza(parcel, 2, entry.zzamJ, false);
        zzb.zzc(parcel, 3, entry.zzamK);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaz(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzcf(n2);
    }

    public StringToIntConverter.Entry zzaz(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        String string2 = null;
        int n4 = 0;
        block5: while (parcel.dataPosition() < n3) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block5;
                }
                case 1: {
                    n4 = zza.zzg(parcel, n5);
                    continue block5;
                }
                case 2: {
                    string2 = zza.zzp(parcel, n5);
                    continue block5;
                }
                case 3: 
            }
            n2 = zza.zzg(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new StringToIntConverter.Entry(n4, string2, n2);
    }

    public StringToIntConverter.Entry[] zzcf(int n2) {
        return new StringToIntConverter.Entry[n2];
    }
}

