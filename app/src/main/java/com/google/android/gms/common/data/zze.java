/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.database.CursorWindow
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze
implements Parcelable.Creator<DataHolder> {
    static void zza(DataHolder dataHolder, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zza(parcel, 1, dataHolder.zzqe(), false);
        zzb.zzc(parcel, 1000, dataHolder.getVersionCode());
        zzb.zza((Parcel)parcel, (int)2, (Parcelable[])dataHolder.zzqf(), (int)n2, (boolean)false);
        zzb.zzc(parcel, 3, dataHolder.getStatusCode());
        zzb.zza(parcel, 4, dataHolder.zzpZ(), false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzak(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzbJ(n2);
    }

    public DataHolder zzak(Parcel object) {
        int n2 = 0;
        Bundle bundle = null;
        int n3 = zza.zzau((Parcel)object);
        CursorWindow[] cursorWindowArray = null;
        String[] stringArray = null;
        int n4 = 0;
        block7: while (object.dataPosition() < n3) {
            int n5 = zza.zzat((Parcel)object);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb((Parcel)object, n5);
                    continue block7;
                }
                case 1: {
                    stringArray = zza.zzB((Parcel)object, n5);
                    continue block7;
                }
                case 1000: {
                    n4 = zza.zzg((Parcel)object, n5);
                    continue block7;
                }
                case 2: {
                    cursorWindowArray = (CursorWindow[])zza.zzb((Parcel)object, n5, CursorWindow.CREATOR);
                    continue block7;
                }
                case 3: {
                    n2 = zza.zzg((Parcel)object, n5);
                    continue block7;
                }
                case 4: 
            }
            bundle = zza.zzr((Parcel)object, n5);
        }
        if (object.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, (Parcel)object);
        }
        object = new DataHolder(n4, stringArray, cursorWindowArray, n2, bundle);
        ((DataHolder)object).zzqd();
        return object;
    }

    public DataHolder[] zzbJ(int n2) {
        return new DataHolder[n2];
    }
}

