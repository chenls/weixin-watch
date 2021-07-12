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
import com.google.android.gms.wearable.internal.GetLocalNodeResponse;
import com.google.android.gms.wearable.internal.NodeParcelable;

public class zzat
implements Parcelable.Creator<GetLocalNodeResponse> {
    static void zza(GetLocalNodeResponse getLocalNodeResponse, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getLocalNodeResponse.versionCode);
        zzb.zzc(parcel, 2, getLocalNodeResponse.statusCode);
        zzb.zza(parcel, 3, getLocalNodeResponse.zzbsL, n2, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziz(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzmd(n2);
    }

    public GetLocalNodeResponse zziz(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        NodeParcelable nodeParcelable = null;
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
            nodeParcelable = zza.zza(parcel, n5, NodeParcelable.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new GetLocalNodeResponse(n4, n2, nodeParcelable);
    }

    public GetLocalNodeResponse[] zzmd(int n2) {
        return new GetLocalNodeResponse[n2];
    }
}

