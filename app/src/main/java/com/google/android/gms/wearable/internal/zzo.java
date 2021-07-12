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
import com.google.android.gms.wearable.internal.ChannelImpl;

public class zzo
implements Parcelable.Creator<ChannelImpl> {
    static void zza(ChannelImpl channelImpl, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, channelImpl.mVersionCode);
        zzb.zza(parcel, 2, channelImpl.getToken(), false);
        zzb.zza(parcel, 3, channelImpl.getNodeId(), false);
        zzb.zza(parcel, 4, channelImpl.getPath(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzig(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlJ(n2);
    }

    public ChannelImpl zzig(Parcel parcel) {
        String string2 = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        String string3 = null;
        String string4 = null;
        block6: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block6;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block6;
                }
                case 2: {
                    string4 = zza.zzp(parcel, n4);
                    continue block6;
                }
                case 3: {
                    string3 = zza.zzp(parcel, n4);
                    continue block6;
                }
                case 4: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new ChannelImpl(n3, string4, string3, string2);
    }

    public ChannelImpl[] zzlJ(int n2) {
        return new ChannelImpl[n2];
    }
}

