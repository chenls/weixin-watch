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
import com.google.android.gms.wearable.internal.MessageEventParcelable;

public class zzba
implements Parcelable.Creator<MessageEventParcelable> {
    static void zza(MessageEventParcelable messageEventParcelable, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, messageEventParcelable.mVersionCode);
        zzb.zzc(parcel, 2, messageEventParcelable.getRequestId());
        zzb.zza(parcel, 3, messageEventParcelable.getPath(), false);
        zzb.zza(parcel, 4, messageEventParcelable.getData(), false);
        zzb.zza(parcel, 5, messageEventParcelable.getSourceNodeId(), false);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziA(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzme(n2);
    }

    public MessageEventParcelable zziA(Parcel parcel) {
        int n2 = 0;
        String string2 = null;
        int n3 = zza.zzau(parcel);
        byte[] byArray = null;
        String string3 = null;
        int n4 = 0;
        block7: while (parcel.dataPosition() < n3) {
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
                    n2 = zza.zzg(parcel, n5);
                    continue block7;
                }
                case 3: {
                    string3 = zza.zzp(parcel, n5);
                    continue block7;
                }
                case 4: {
                    byArray = zza.zzs(parcel, n5);
                    continue block7;
                }
                case 5: 
            }
            string2 = zza.zzp(parcel, n5);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new MessageEventParcelable(n4, n2, string3, byArray, string2);
    }

    public MessageEventParcelable[] zzme(int n2) {
        return new MessageEventParcelable[n2];
    }
}

