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
import com.google.android.gms.wearable.internal.GetCloudSyncSettingResponse;

public class zzan
implements Parcelable.Creator<GetCloudSyncSettingResponse> {
    static void zza(GetCloudSyncSettingResponse getCloudSyncSettingResponse, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getCloudSyncSettingResponse.versionCode);
        zzb.zzc(parcel, 2, getCloudSyncSettingResponse.statusCode);
        zzb.zza(parcel, 3, getCloudSyncSettingResponse.enabled);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzit(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlX(n2);
    }

    public GetCloudSyncSettingResponse zzit(Parcel parcel) {
        boolean bl2 = false;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        int n4 = 0;
        block5: while (parcel.dataPosition() < n2) {
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
                    n3 = zza.zzg(parcel, n5);
                    continue block5;
                }
                case 3: 
            }
            bl2 = zza.zzc(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new GetCloudSyncSettingResponse(n4, n3, bl2);
    }

    public GetCloudSyncSettingResponse[] zzlX(int n2) {
        return new GetCloudSyncSettingResponse[n2];
    }
}

