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
import com.google.android.gms.wearable.internal.NodeParcelable;

public class zzbc
implements Parcelable.Creator<NodeParcelable> {
    static void zza(NodeParcelable nodeParcelable, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, nodeParcelable.mVersionCode);
        zzb.zza(parcel, 2, nodeParcelable.getId(), false);
        zzb.zza(parcel, 3, nodeParcelable.getDisplayName(), false);
        zzb.zzc(parcel, 4, nodeParcelable.getHopCount());
        zzb.zza(parcel, 5, nodeParcelable.isNearby());
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziB(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzmf(n2);
    }

    public NodeParcelable zziB(Parcel parcel) {
        String string2 = null;
        boolean bl2 = false;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        String string3 = null;
        int n4 = 0;
        block7: while (parcel.dataPosition() < n2) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block7;
                }
                case 1: {
                    n4 = zza.zzg(parcel, n5);
                    continue block7;
                }
                case 2: {
                    string3 = zza.zzp(parcel, n5);
                    continue block7;
                }
                case 3: {
                    string2 = zza.zzp(parcel, n5);
                    continue block7;
                }
                case 4: {
                    n3 = zza.zzg(parcel, n5);
                    continue block7;
                }
                case 5: 
            }
            bl2 = zza.zzc(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new NodeParcelable(n4, string3, string2, n3, bl2);
    }

    public NodeParcelable[] zzmf(int n2) {
        return new NodeParcelable[n2];
    }
}

