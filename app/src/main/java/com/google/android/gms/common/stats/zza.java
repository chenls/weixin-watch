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
import com.google.android.gms.common.stats.ConnectionEvent;

public class zza
implements Parcelable.Creator<ConnectionEvent> {
    static void zza(ConnectionEvent connectionEvent, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, connectionEvent.mVersionCode);
        zzb.zza(parcel, 2, connectionEvent.getTimeMillis());
        zzb.zza(parcel, 4, connectionEvent.zzrF(), false);
        zzb.zza(parcel, 5, connectionEvent.zzrG(), false);
        zzb.zza(parcel, 6, connectionEvent.zzrH(), false);
        zzb.zza(parcel, 7, connectionEvent.zzrI(), false);
        zzb.zza(parcel, 8, connectionEvent.zzrJ(), false);
        zzb.zza(parcel, 10, connectionEvent.zzrN());
        zzb.zza(parcel, 11, connectionEvent.zzrM());
        zzb.zzc(parcel, 12, connectionEvent.getEventType());
        zzb.zza(parcel, 13, connectionEvent.zzrK(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaF(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzcl(n2);
    }

    public ConnectionEvent zzaF(Parcel parcel) {
        int n2 = com.google.android.gms.common.internal.safeparcel.zza.zzau(parcel);
        int n3 = 0;
        long l2 = 0L;
        int n4 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        long l3 = 0L;
        long l4 = 0L;
        block13: while (parcel.dataPosition() < n2) {
            int n5 = com.google.android.gms.common.internal.safeparcel.zza.zzat(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzca(n5)) {
                default: {
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, n5);
                    continue block13;
                }
                case 1: {
                    n3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n5);
                    continue block13;
                }
                case 2: {
                    l2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, n5);
                    continue block13;
                }
                case 4: {
                    string2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n5);
                    continue block13;
                }
                case 5: {
                    string3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n5);
                    continue block13;
                }
                case 6: {
                    string4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n5);
                    continue block13;
                }
                case 7: {
                    string5 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n5);
                    continue block13;
                }
                case 8: {
                    string6 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n5);
                    continue block13;
                }
                case 10: {
                    l3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, n5);
                    continue block13;
                }
                case 11: {
                    l4 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, n5);
                    continue block13;
                }
                case 12: {
                    n4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, n5);
                    continue block13;
                }
                case 13: 
            }
            string7 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new ConnectionEvent(n3, l2, n4, string2, string3, string4, string5, string6, string7, l3, l4);
    }

    public ConnectionEvent[] zzcl(int n2) {
        return new ConnectionEvent[n2];
    }
}

