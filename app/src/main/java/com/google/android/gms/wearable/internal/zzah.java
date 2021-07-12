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
import com.google.android.gms.wearable.internal.CapabilityInfoParcelable;
import com.google.android.gms.wearable.internal.GetAllCapabilitiesResponse;
import java.util.ArrayList;

public class zzah
implements Parcelable.Creator<GetAllCapabilitiesResponse> {
    static void zza(GetAllCapabilitiesResponse getAllCapabilitiesResponse, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getAllCapabilitiesResponse.versionCode);
        zzb.zzc(parcel, 2, getAllCapabilitiesResponse.statusCode);
        zzb.zzc(parcel, 3, getAllCapabilitiesResponse.zzbsA, false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzin(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlR(n2);
    }

    public GetAllCapabilitiesResponse zzin(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        ArrayList<CapabilityInfoParcelable> arrayList = null;
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
                    n2 = zza.zzg(parcel, n5);
                    continue block5;
                }
                case 3: 
            }
            arrayList = zza.zzc(parcel, n5, CapabilityInfoParcelable.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new GetAllCapabilitiesResponse(n4, n2, arrayList);
    }

    public GetAllCapabilitiesResponse[] zzlR(int n2) {
        return new GetAllCapabilitiesResponse[n2];
    }
}

