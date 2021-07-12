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
import com.google.android.gms.wearable.internal.ChannelEventParcelable;
import com.google.android.gms.wearable.internal.ChannelImpl;

public class zzn
implements Parcelable.Creator<ChannelEventParcelable> {
    static void zza(ChannelEventParcelable channelEventParcelable, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, channelEventParcelable.mVersionCode);
        zzb.zza(parcel, 2, channelEventParcelable.zzbsc, n2, false);
        zzb.zzc(parcel, 3, channelEventParcelable.type);
        zzb.zzc(parcel, 4, channelEventParcelable.zzbsa);
        zzb.zzc(parcel, 5, channelEventParcelable.zzbsb);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzif(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlI(n2);
    }

    public ChannelEventParcelable zzif(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        ChannelImpl channelImpl = null;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        block7: while (parcel.dataPosition() < n3) {
            int n7 = zza.zzat(parcel);
            switch (zza.zzca(n7)) {
                default: {
                    zza.zzb(parcel, n7);
                    continue block7;
                }
                case 1: {
                    n6 = zza.zzg(parcel, n7);
                    continue block7;
                }
                case 2: {
                    channelImpl = zza.zza(parcel, n7, ChannelImpl.CREATOR);
                    continue block7;
                }
                case 3: {
                    n5 = zza.zzg(parcel, n7);
                    continue block7;
                }
                case 4: {
                    n4 = zza.zzg(parcel, n7);
                    continue block7;
                }
                case 5: 
            }
            n2 = zza.zzg(parcel, n7);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new ChannelEventParcelable(n6, channelImpl, n5, n4, n2);
    }

    public ChannelEventParcelable[] zzlI(int n2) {
        return new ChannelEventParcelable[n2];
    }
}

