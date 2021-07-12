/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.clearcut;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.playlog.internal.PlayLoggerContext;

public class zzd
implements Parcelable.Creator<LogEventParcelable> {
    static void zza(LogEventParcelable logEventParcelable, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, logEventParcelable.versionCode);
        zzb.zza(parcel, 2, logEventParcelable.zzafh, n2, false);
        zzb.zza(parcel, 3, logEventParcelable.zzafi, false);
        zzb.zza(parcel, 4, logEventParcelable.zzafj, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzaf(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbs(n2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public LogEventParcelable zzaf(Parcel parcel) {
        void var6_7;
        int[] nArray = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        PlayLoggerContext playLoggerContext = null;
        Object var6_6 = null;
        while (parcel.dataPosition() < n2) {
            void var6_9;
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    PlayLoggerContext playLoggerContext2 = playLoggerContext;
                    playLoggerContext = var6_7;
                    PlayLoggerContext playLoggerContext3 = playLoggerContext2;
                    break;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    void var8_19 = var6_7;
                    PlayLoggerContext playLoggerContext4 = playLoggerContext;
                    playLoggerContext = var8_19;
                    break;
                }
                case 2: {
                    PlayLoggerContext playLoggerContext5 = zza.zza(parcel, n4, PlayLoggerContext.CREATOR);
                    PlayLoggerContext playLoggerContext6 = playLoggerContext;
                    playLoggerContext = playLoggerContext5;
                    break;
                }
                case 3: {
                    byte[] byArray = zza.zzs(parcel, n4);
                    playLoggerContext = var6_7;
                    byte[] byArray2 = byArray;
                    break;
                }
                case 4: {
                    nArray = zza.zzv(parcel, n4);
                    void var8_22 = var6_7;
                    PlayLoggerContext playLoggerContext7 = playLoggerContext;
                    playLoggerContext = var8_22;
                }
            }
            PlayLoggerContext playLoggerContext8 = playLoggerContext;
            playLoggerContext = var6_9;
            PlayLoggerContext playLoggerContext9 = playLoggerContext8;
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new LogEventParcelable(n3, (PlayLoggerContext)var6_7, (byte[])playLoggerContext, nArray);
    }

    public LogEventParcelable[] zzbs(int n2) {
        return new LogEventParcelable[n2];
    }
}

