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
import com.google.android.gms.wearable.ConnectionConfiguration;
import com.google.android.gms.wearable.internal.GetConfigResponse;

public class zzao
implements Parcelable.Creator<GetConfigResponse> {
    static void zza(GetConfigResponse getConfigResponse, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getConfigResponse.versionCode);
        zzb.zzc(parcel, 2, getConfigResponse.statusCode);
        zzb.zza(parcel, 3, getConfigResponse.zzbsG, n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziu(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlY(n2);
    }

    public GetConfigResponse zziu(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        ConnectionConfiguration connectionConfiguration = null;
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
            connectionConfiguration = zza.zza(parcel, n5, ConnectionConfiguration.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new GetConfigResponse(n4, n2, connectionConfiguration);
    }

    public GetConfigResponse[] zzlY(int n2) {
        return new GetConfigResponse[n2];
    }
}

