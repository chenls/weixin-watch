/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.internal.RemoveListenerRequest;

public class zzbg
implements Parcelable.Creator<RemoveListenerRequest> {
    static void zza(RemoveListenerRequest removeListenerRequest, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, removeListenerRequest.mVersionCode);
        zzb.zza(parcel, 2, removeListenerRequest.zzIy(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziF(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzmj(n2);
    }

    public RemoveListenerRequest zziF(Parcel parcel) {
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        IBinder iBinder = null;
        block4: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block4;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block4;
                }
                case 2: 
            }
            iBinder = zza.zzq(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new RemoveListenerRequest(n3, iBinder);
    }

    public RemoveListenerRequest[] zzmj(int n2) {
        return new RemoveListenerRequest[n2];
    }
}

