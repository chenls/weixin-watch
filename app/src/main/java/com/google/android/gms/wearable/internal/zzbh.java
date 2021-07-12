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
import com.google.android.gms.wearable.internal.RemoveLocalCapabilityResponse;

public class zzbh
implements Parcelable.Creator<RemoveLocalCapabilityResponse> {
    static void zza(RemoveLocalCapabilityResponse removeLocalCapabilityResponse, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, removeLocalCapabilityResponse.versionCode);
        zzb.zzc(parcel, 2, removeLocalCapabilityResponse.statusCode);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziG(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzmk(n2);
    }

    public RemoveLocalCapabilityResponse zziG(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        int n4 = 0;
        block4: while (parcel.dataPosition() < n3) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block4;
                }
                case 1: {
                    n4 = zza.zzg(parcel, n5);
                    continue block4;
                }
                case 2: 
            }
            n2 = zza.zzg(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new RemoveLocalCapabilityResponse(n4, n2);
    }

    public RemoveLocalCapabilityResponse[] zzmk(int n2) {
        return new RemoveLocalCapabilityResponse[n2];
    }
}

