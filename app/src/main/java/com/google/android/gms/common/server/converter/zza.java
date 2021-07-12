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
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.common.server.converter.StringToIntConverter;

public class zza
implements Parcelable.Creator<ConverterWrapper> {
    static void zza(ConverterWrapper converterWrapper, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, converterWrapper.getVersionCode());
        zzb.zza(parcel, 2, converterWrapper.zzrg(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzax(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzcd(n2);
    }

    public ConverterWrapper zzax(Parcel parcel) {
        int n2 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        int n3 = 0;
        StringToIntConverter stringToIntConverter = null;
        block4: while (parcel.dataPosition() < n2) {
            int n4 = com.google.android.gms.common.internal.safeparcel.zza.zzat(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzca(n4)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            stringToIntConverter = com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, n4, StringToIntConverter.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new ConverterWrapper(n3, stringToIntConverter);
    }

    public ConverterWrapper[] zzcd(int n2) {
        return new ConverterWrapper[n2];
    }
}

