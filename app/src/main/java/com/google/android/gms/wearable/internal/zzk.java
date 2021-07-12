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
import com.google.android.gms.wearable.internal.NodeParcelable;
import java.util.ArrayList;

public class zzk
implements Parcelable.Creator<CapabilityInfoParcelable> {
    static void zza(CapabilityInfoParcelable capabilityInfoParcelable, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, capabilityInfoParcelable.mVersionCode);
        zzb.zza(parcel, 2, capabilityInfoParcelable.getName(), false);
        zzb.zzc(parcel, 3, capabilityInfoParcelable.zzII(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzie(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlF(n2);
    }

    public CapabilityInfoParcelable zzie(Parcel parcel) {
        ArrayList<NodeParcelable> arrayList = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        String string2 = null;
        block5: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block5;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block5;
                }
                case 2: {
                    string2 = zza.zzp(parcel, n4);
                    continue block5;
                }
                case 3: 
            }
            arrayList = zza.zzc(parcel, n4, NodeParcelable.CREATOR);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new CapabilityInfoParcelable(n3, string2, arrayList);
    }

    public CapabilityInfoParcelable[] zzlF(int n2) {
        return new CapabilityInfoParcelable[n2];
    }
}

