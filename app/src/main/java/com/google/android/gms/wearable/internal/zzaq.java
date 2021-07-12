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
import com.google.android.gms.wearable.internal.GetConnectedNodesResponse;
import com.google.android.gms.wearable.internal.NodeParcelable;
import java.util.ArrayList;

public class zzaq
implements Parcelable.Creator<GetConnectedNodesResponse> {
    static void zza(GetConnectedNodesResponse getConnectedNodesResponse, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, getConnectedNodesResponse.versionCode);
        zzb.zzc(parcel, 2, getConnectedNodesResponse.statusCode);
        zzb.zzc(parcel, 3, getConnectedNodesResponse.zzbsI, false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziw(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzma(n2);
    }

    public GetConnectedNodesResponse zziw(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        ArrayList<NodeParcelable> arrayList = null;
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
            arrayList = zza.zzc(parcel, n5, NodeParcelable.CREATOR);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new GetConnectedNodesResponse(n4, n2, arrayList);
    }

    public GetConnectedNodesResponse[] zzma(int n2) {
        return new GetConnectedNodesResponse[n2];
    }
}

