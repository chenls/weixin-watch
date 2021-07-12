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
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.server.response.FieldMappingDictionary;

public class zzb
implements Parcelable.Creator<FieldMappingDictionary.FieldMapPair> {
    static void zza(FieldMappingDictionary.FieldMapPair fieldMapPair, Parcel parcel, int n2) {
        int n3 = com.google.android.gms.common.internal.safeparcel.zzb.zzav(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, fieldMapPair.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, fieldMapPair.key, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, fieldMapPair.zzamZ, n2, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaB(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzch(n2);
    }

    public FieldMappingDictionary.FieldMapPair zzaB(Parcel parcel) {
        FastJsonResponse.Field field = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        String string2 = null;
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
                    string2 = zza.zzp(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            field = zza.zza(parcel, n4, FastJsonResponse.Field.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new FieldMappingDictionary.FieldMapPair(n3, string2, field);
    }

    public FieldMappingDictionary.FieldMapPair[] zzch(int n2) {
        return new FieldMappingDictionary.FieldMapPair[n2];
    }
}

