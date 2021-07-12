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
import com.google.android.gms.wearable.internal.AncsNotificationParcelable;

public class zzh
implements Parcelable.Creator<AncsNotificationParcelable> {
    static void zza(AncsNotificationParcelable ancsNotificationParcelable, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, ancsNotificationParcelable.mVersionCode);
        zzb.zzc(parcel, 2, ancsNotificationParcelable.getId());
        zzb.zza(parcel, 3, ancsNotificationParcelable.zzwK(), false);
        zzb.zza(parcel, 4, ancsNotificationParcelable.zzIB(), false);
        zzb.zza(parcel, 5, ancsNotificationParcelable.zzIC(), false);
        zzb.zza(parcel, 6, ancsNotificationParcelable.getTitle(), false);
        zzb.zza(parcel, 7, ancsNotificationParcelable.zzwc(), false);
        zzb.zza(parcel, 8, ancsNotificationParcelable.getDisplayName(), false);
        zzb.zza(parcel, 9, ancsNotificationParcelable.zzID());
        zzb.zza(parcel, 10, ancsNotificationParcelable.zzIE());
        zzb.zza(parcel, 11, ancsNotificationParcelable.zzIF());
        zzb.zza(parcel, 12, ancsNotificationParcelable.zzIG());
        zzb.zza(parcel, 13, ancsNotificationParcelable.getPackageName(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzid(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlE(n2);
    }

    public AncsNotificationParcelable zzid(Parcel parcel) {
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        int n4 = 0;
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        String string7 = null;
        byte by2 = 0;
        byte by3 = 0;
        byte by4 = 0;
        byte by5 = 0;
        String string8 = null;
        block15: while (parcel.dataPosition() < n2) {
            int n5 = zza.zzat(parcel);
            switch (zza.zzca(n5)) {
                default: {
                    zza.zzb(parcel, n5);
                    continue block15;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n5);
                    continue block15;
                }
                case 2: {
                    n4 = zza.zzg(parcel, n5);
                    continue block15;
                }
                case 3: {
                    string2 = zza.zzp(parcel, n5);
                    continue block15;
                }
                case 4: {
                    string3 = zza.zzp(parcel, n5);
                    continue block15;
                }
                case 5: {
                    string4 = zza.zzp(parcel, n5);
                    continue block15;
                }
                case 6: {
                    string5 = zza.zzp(parcel, n5);
                    continue block15;
                }
                case 7: {
                    string6 = zza.zzp(parcel, n5);
                    continue block15;
                }
                case 8: {
                    string7 = zza.zzp(parcel, n5);
                    continue block15;
                }
                case 9: {
                    by2 = zza.zze(parcel, n5);
                    continue block15;
                }
                case 10: {
                    by3 = zza.zze(parcel, n5);
                    continue block15;
                }
                case 11: {
                    by4 = zza.zze(parcel, n5);
                    continue block15;
                }
                case 12: {
                    by5 = zza.zze(parcel, n5);
                    continue block15;
                }
                case 13: 
            }
            string8 = zza.zzp(parcel, n5);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new AncsNotificationParcelable(n3, n4, string2, string3, string4, string5, string6, string7, by2, by3, by4, by5, string8);
    }

    public AncsNotificationParcelable[] zzlE(int n2) {
        return new AncsNotificationParcelable[n2];
    }
}

