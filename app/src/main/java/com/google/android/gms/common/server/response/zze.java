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
import com.google.android.gms.common.server.response.SafeParcelResponse;

public class zze
implements Parcelable.Creator<SafeParcelResponse> {
    static void zza(SafeParcelResponse safeParcelResponse, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, safeParcelResponse.getVersionCode());
        zzb.zza(parcel, 2, safeParcelResponse.zzrD(), false);
        zzb.zza(parcel, 3, safeParcelResponse.zzrE(), n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaE(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzck(n2);
    }

    public SafeParcelResponse zzaE(Parcel parcel) {
        FieldMappingDictionary fieldMappingDictionary = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        Parcel parcel2 = null;
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
                    parcel2 = zza.zzE(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            fieldMappingDictionary = zza.zza(parcel, n4, FieldMappingDictionary.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new SafeParcelResponse(n3, parcel2, fieldMappingDictionary);
    }

    public SafeParcelResponse[] zzck(int n2) {
        return new SafeParcelResponse[n2];
    }
}

