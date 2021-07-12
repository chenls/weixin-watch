/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.playlog.internal.LogEvent;

public class zzc
implements Parcelable.Creator<LogEvent> {
    static void zza(LogEvent logEvent, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, logEvent.versionCode);
        zzb.zza(parcel, 2, logEvent.zzbdA);
        zzb.zza(parcel, 3, logEvent.tag, false);
        zzb.zza(parcel, 4, logEvent.zzbdC, false);
        zzb.zza(parcel, 5, logEvent.zzbdD, false);
        zzb.zza(parcel, 6, logEvent.zzbdB);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzgy(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzjE(n2);
    }

    public LogEvent zzgy(Parcel parcel) {
        long l2 = 0L;
        Bundle bundle = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        byte[] byArray = null;
        String string2 = null;
        long l3 = 0L;
        block8: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block8;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block8;
                }
                case 2: {
                    l3 = zza.zzi(parcel, n4);
                    continue block8;
                }
                case 3: {
                    string2 = zza.zzp(parcel, n4);
                    continue block8;
                }
                case 4: {
                    byArray = zza.zzs(parcel, n4);
                    continue block8;
                }
                case 5: {
                    bundle = zza.zzr(parcel, n4);
                    continue block8;
                }
                case 6: 
            }
            l2 = zza.zzi(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new LogEvent(n3, l3, l2, string2, byArray, bundle);
    }

    public LogEvent[] zzjE(int n2) {
        return new LogEvent[n2];
    }
}

