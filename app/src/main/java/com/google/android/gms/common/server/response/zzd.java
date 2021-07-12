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

public class zzd
implements Parcelable.Creator<FieldMappingDictionary.Entry> {
    static void zza(FieldMappingDictionary.Entry entry, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, entry.versionCode);
        zzb.zza(parcel, 2, entry.className, false);
        zzb.zzc(parcel, 3, entry.zzamY, false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaD(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzcj(n2);
    }

    public FieldMappingDictionary.Entry zzaD(Parcel parcel) {
        ArrayList<FieldMappingDictionary.FieldMapPair> arrayList = null;
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
            arrayList = zza.zzc(parcel, n4, FieldMappingDictionary.FieldMapPair.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new FieldMappingDictionary.Entry(n3, string2, arrayList);
    }

    public FieldMappingDictionary.Entry[] zzcj(int n2) {
        return new FieldMappingDictionary.Entry[n2];
    }
}

