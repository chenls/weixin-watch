/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.stats.WakeLockEvent;
import java.util.ArrayList;

public class zzh
implements Parcelable.Creator<WakeLockEvent> {
    static void zza(WakeLockEvent wakeLockEvent, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, wakeLockEvent.mVersionCode);
        zzb.zza(parcel, 2, wakeLockEvent.getTimeMillis());
        zzb.zza(parcel, 4, wakeLockEvent.zzrR(), false);
        zzb.zzc(parcel, 5, wakeLockEvent.zzrT());
        zzb.zzb(parcel, 6, wakeLockEvent.zzrU(), false);
        zzb.zza(parcel, 8, wakeLockEvent.zzrN());
        zzb.zza(parcel, 10, wakeLockEvent.zzrS(), false);
        zzb.zzc(parcel, 11, wakeLockEvent.getEventType());
        zzb.zza(parcel, 12, wakeLockEvent.zzrK(), false);
        zzb.zza(parcel, 13, wakeLockEvent.zzrW(), false);
        zzb.zzc(parcel, 14, wakeLockEvent.zzrV());
        zzb.zza(parcel, 15, wakeLockEvent.zzrX());
        zzb.zza(parcel, 16, wakeLockEvent.zzrY());
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaG(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzcm(n2);
    }

    public WakeLockEvent zzaG(Parcel parcel) {
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        long l2 = 0L;
        int n4 = 0;
        String string2 = null;
        int n5 = 0;
        ArrayList<String> arrayList = null;
        String string3 = null;
        long l3 = 0L;
        int n6 = 0;
        String string4 = null;
        String string5 = null;
        float f2 = 0.0f;
        long l4 = 0L;
        block15: while (parcel.dataPosition() < n2) {
            int n7 = zza.zzat(parcel);
            switch (zza.zzca(n7)) {
                default: {
                    zza.zzb(parcel, n7);
                    continue block15;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n7);
                    continue block15;
                }
                case 2: {
                    l2 = zza.zzi(parcel, n7);
                    continue block15;
                }
                case 4: {
                    string2 = zza.zzp(parcel, n7);
                    continue block15;
                }
                case 5: {
                    n5 = zza.zzg(parcel, n7);
                    continue block15;
                }
                case 6: {
                    arrayList = zza.zzD(parcel, n7);
                    continue block15;
                }
                case 8: {
                    l3 = zza.zzi(parcel, n7);
                    continue block15;
                }
                case 10: {
                    string4 = zza.zzp(parcel, n7);
                    continue block15;
                }
                case 11: {
                    n4 = zza.zzg(parcel, n7);
                    continue block15;
                }
                case 12: {
                    string3 = zza.zzp(parcel, n7);
                    continue block15;
                }
                case 13: {
                    string5 = zza.zzp(parcel, n7);
                    continue block15;
                }
                case 14: {
                    n6 = zza.zzg(parcel, n7);
                    continue block15;
                }
                case 15: {
                    f2 = zza.zzl(parcel, n7);
                    continue block15;
                }
                case 16: 
            }
            l4 = zza.zzi(parcel, n7);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new WakeLockEvent(n3, l2, n4, string2, n5, arrayList, string3, l3, n6, string4, string5, f2, l4);
    }

    public WakeLockEvent[] zzcm(int n2) {
        return new WakeLockEvent[n2];
    }
}

