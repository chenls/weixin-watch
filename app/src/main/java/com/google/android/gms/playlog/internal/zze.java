/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.playlog.internal.PlayLoggerContext;

public class zze
implements Parcelable.Creator<PlayLoggerContext> {
    static void zza(PlayLoggerContext playLoggerContext, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, playLoggerContext.versionCode);
        zzb.zza(parcel, 2, playLoggerContext.packageName, false);
        zzb.zzc(parcel, 3, playLoggerContext.zzbdL);
        zzb.zzc(parcel, 4, playLoggerContext.zzbdM);
        zzb.zza(parcel, 5, playLoggerContext.zzbdN, false);
        zzb.zza(parcel, 6, playLoggerContext.zzbdO, false);
        zzb.zza(parcel, 7, playLoggerContext.zzbdP);
        zzb.zza(parcel, 8, playLoggerContext.zzbdQ, false);
        zzb.zza(parcel, 9, playLoggerContext.zzbdR);
        zzb.zzc(parcel, 10, playLoggerContext.zzbdS);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzgz(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzjF(n2);
    }

    public PlayLoggerContext zzgz(Parcel parcel) {
        String string2 = null;
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        boolean bl2 = true;
        boolean bl3 = false;
        String string3 = null;
        String string4 = null;
        int n4 = 0;
        int n5 = 0;
        String string5 = null;
        int n6 = 0;
        block12: while (parcel.dataPosition() < n3) {
            int n7 = zza.zzat(parcel);
            switch (zza.zzca(n7)) {
                default: {
                    zza.zzb(parcel, n7);
                    continue block12;
                }
                case 1: {
                    n6 = zza.zzg(parcel, n7);
                    continue block12;
                }
                case 2: {
                    string5 = zza.zzp(parcel, n7);
                    continue block12;
                }
                case 3: {
                    n5 = zza.zzg(parcel, n7);
                    continue block12;
                }
                case 4: {
                    n4 = zza.zzg(parcel, n7);
                    continue block12;
                }
                case 5: {
                    string4 = zza.zzp(parcel, n7);
                    continue block12;
                }
                case 6: {
                    string3 = zza.zzp(parcel, n7);
                    continue block12;
                }
                case 7: {
                    bl2 = zza.zzc(parcel, n7);
                    continue block12;
                }
                case 8: {
                    string2 = zza.zzp(parcel, n7);
                    continue block12;
                }
                case 9: {
                    bl3 = zza.zzc(parcel, n7);
                    continue block12;
                }
                case 10: 
            }
            n2 = zza.zzg(parcel, n7);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new PlayLoggerContext(n6, string5, n5, n4, string4, string3, bl2, string2, bl3, n2);
    }

    public PlayLoggerContext[] zzjF(int n2) {
        return new PlayLoggerContext[n2];
    }
}

