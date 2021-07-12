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
import com.google.android.gms.common.server.converter.StringToIntConverter;
import java.util.ArrayList;

public class zzb
implements Parcelable.Creator<StringToIntConverter> {
    static void zza(StringToIntConverter stringToIntConverter, Parcel parcel, int n2) {
        n2 = com.google.android.gms.common.internal.safeparcel.zzb.zzav(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, stringToIntConverter.getVersionCode());
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, stringToIntConverter.zzri(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzay(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzce(n2);
    }

    public StringToIntConverter zzay(Parcel parcel) {
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        ArrayList<StringToIntConverter.Entry> arrayList = null;
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
            arrayList = zza.zzc(parcel, n4, StringToIntConverter.Entry.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new StringToIntConverter(n3, arrayList);
    }

    public StringToIntConverter[] zzce(int n2) {
        return new StringToIntConverter[n2];
    }
}

