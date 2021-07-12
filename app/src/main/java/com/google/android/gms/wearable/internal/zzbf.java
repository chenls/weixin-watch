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
import com.google.android.gms.wearable.internal.DataItemParcelable;
import com.google.android.gms.wearable.internal.PutDataResponse;

public class zzbf
implements Parcelable.Creator<PutDataResponse> {
    static void zza(PutDataResponse putDataResponse, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, putDataResponse.versionCode);
        zzb.zzc(parcel, 2, putDataResponse.statusCode);
        zzb.zza(parcel, 3, putDataResponse.zzbsJ, n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziE(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzmi(n2);
    }

    public PutDataResponse zziE(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        DataItemParcelable dataItemParcelable = null;
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
            dataItemParcelable = zza.zza(parcel, n5, DataItemParcelable.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new PutDataResponse(n4, n2, dataItemParcelable);
    }

    public PutDataResponse[] zzmi(int n2) {
        return new PutDataResponse[n2];
    }
}

