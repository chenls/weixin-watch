/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.response.FieldMappingDictionary;
import java.util.ArrayList;

public class zzc
implements Parcelable.Creator<FieldMappingDictionary> {
    static void zza(FieldMappingDictionary fieldMappingDictionary, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, fieldMappingDictionary.getVersionCode());
        zzb.zzc(parcel, 2, fieldMappingDictionary.zzrA(), false);
        zzb.zza(parcel, 3, fieldMappingDictionary.zzrB(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaC(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzci(n2);
    }

    public FieldMappingDictionary zzaC(Parcel parcel) {
        String string2 = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        ArrayList<FieldMappingDictionary.Entry> arrayList = null;
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
                    arrayList = zza.zzc(parcel, n4, FieldMappingDictionary.Entry.CREATOR);
                    continue block5;
                }
                case 3: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new FieldMappingDictionary(n3, arrayList, string2);
    }

    public FieldMappingDictionary[] zzci(int n2) {
        return new FieldMappingDictionary[n2];
    }
}

