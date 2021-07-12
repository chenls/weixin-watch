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
import com.google.android.gms.wearable.internal.GetCloudSyncOptInStatusResponse;

public class zzam
implements Parcelable.Creator<GetCloudSyncOptInStatusResponse> {
    static void zza(GetCloudSyncOptInStatusResponse getCloudSyncOptInStatusResponse, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getCloudSyncOptInStatusResponse.versionCode);
        zzb.zzc(parcel, 2, getCloudSyncOptInStatusResponse.statusCode);
        zzb.zza(parcel, 3, getCloudSyncOptInStatusResponse.zzbsE);
        zzb.zza(parcel, 4, getCloudSyncOptInStatusResponse.zzbsF);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzis(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlW(n2);
    }

    public GetCloudSyncOptInStatusResponse zzis(Parcel parcel) {
        boolean bl2 = false;
        int n2 = zza.zzau(parcel);
        boolean bl3 = false;
        int n3 = 0;
        int n4 = 0;
        block6: while (parcel.dataPosition() < n2) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block6;
                }
                case 1: {
                    n4 = zza.zzg(parcel, n5);
                    continue block6;
                }
                case 2: {
                    n3 = zza.zzg(parcel, n5);
                    continue block6;
                }
                case 3: {
                    bl3 = zza.zzc(parcel, n5);
                    continue block6;
                }
                case 4: 
            }
            bl2 = zza.zzc(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new GetCloudSyncOptInStatusResponse(n4, n3, bl3, bl2);
    }

    public GetCloudSyncOptInStatusResponse[] zzlW(int n2) {
        return new GetCloudSyncOptInStatusResponse[n2];
    }
}

