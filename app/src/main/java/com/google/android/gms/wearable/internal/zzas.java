/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.internal.GetFdForAssetResponse;

public class zzas
implements Parcelable.Creator<GetFdForAssetResponse> {
    static void zza(GetFdForAssetResponse getFdForAssetResponse, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getFdForAssetResponse.versionCode);
        zzb.zzc(parcel, 2, getFdForAssetResponse.statusCode);
        zzb.zza(parcel, 3, (Parcelable)getFdForAssetResponse.zzbsK, n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziy(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzmc(n2);
    }

    public GetFdForAssetResponse zziy(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        ParcelFileDescriptor parcelFileDescriptor = null;
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
            parcelFileDescriptor = (ParcelFileDescriptor)zza.zza(parcel, n5, ParcelFileDescriptor.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new GetFdForAssetResponse(n4, n2, parcelFileDescriptor);
    }

    public GetFdForAssetResponse[] zzmc(int n2) {
        return new GetFdForAssetResponse[n2];
    }
}

