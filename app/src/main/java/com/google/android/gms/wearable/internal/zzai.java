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
import com.google.android.gms.wearable.internal.GetCapabilityResponse;

public class zzai
implements Parcelable.Creator<GetCapabilityResponse> {
    static void zza(GetCapabilityResponse getCapabilityResponse, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getCapabilityResponse.versionCode);
        zzb.zzc(parcel, 2, getCapabilityResponse.statusCode);
        zzb.zza(parcel, 3, getCapabilityResponse.zzbsB, n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzio(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlS(n2);
    }

    public GetCapabilityResponse zzio(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        CapabilityInfoParcelable capabilityInfoParcelable = null;
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
            capabilityInfoParcelable = zza.zza(parcel, n5, CapabilityInfoParcelable.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new GetCapabilityResponse(n4, n2, capabilityInfoParcelable);
    }

    public GetCapabilityResponse[] zzlS(int n2) {
        return new GetCapabilityResponse[n2];
    }
}

