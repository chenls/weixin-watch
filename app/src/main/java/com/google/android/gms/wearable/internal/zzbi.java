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
import com.google.android.gms.wearable.internal.SendMessageResponse;

public class zzbi
implements Parcelable.Creator<SendMessageResponse> {
    static void zza(SendMessageResponse sendMessageResponse, Parcel parcel, int n2) {
        n2 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, sendMessageResponse.versionCode);
        zzb.zzc(parcel, 2, sendMessageResponse.statusCode);
        zzb.zzc(parcel, 3, sendMessageResponse.zzaNj);
        zzb.zzI(parcel, n2);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zziH(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzml(n2);
    }

    public SendMessageResponse zziH(Parcel parcel) {
        int n2 = 0;
        int n3 = zza.zzau(parcel);
        int n4 = 0;
        int n5 = 0;
        block5: while (parcel.dataPosition() < n3) {
            int n6 = zza.zzat(parcel);
            switch (zza.zzca(n6)) {
                default: {
                    zza.zzb(parcel, n6);
                    continue block5;
                }
                case 1: {
                    n5 = zza.zzg(parcel, n6);
                    continue block5;
                }
                case 2: {
                    n4 = zza.zzg(parcel, n6);
                    continue block5;
                }
                case 3: 
            }
            n2 = zza.zzg(parcel, n6);
        }
        if (parcel.dataPosition() != n3) {
            throw new zza.zza("Overread allowed size end=" + n3, parcel);
        }
        return new SendMessageResponse(n5, n4, n2);
    }

    public SendMessageResponse[] zzml(int n2) {
        return new SendMessageResponse[n2];
    }
}

