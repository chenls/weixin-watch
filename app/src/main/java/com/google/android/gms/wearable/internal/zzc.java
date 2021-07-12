/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.wearable.internal.AddListenerRequest;

public class zzc
implements Parcelable.Creator<AddListenerRequest> {
    static void zza(AddListenerRequest addListenerRequest, Parcel parcel, int n2) {
        int n3 = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, addListenerRequest.mVersionCode);
        zzb.zza(parcel, 2, addListenerRequest.zzIy(), false);
        zzb.zza((Parcel)parcel, (int)3, (Parcelable[])addListenerRequest.zzbrC, (int)n2, (boolean)false);
        zzb.zza(parcel, 4, addListenerRequest.zzbrD, false);
        zzb.zza(parcel, 5, addListenerRequest.zzbrE, false);
        zzb.zzI(parcel, n3);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return this.zzia(parcel);
    }

    public /* synthetic */ Object[] newArray(int n2) {
        return this.zzlB(n2);
    }

    public AddListenerRequest zzia(Parcel parcel) {
        String string2 = null;
        int n2 = zza.zzau(parcel);
        int n3 = 0;
        String string3 = null;
        IntentFilter[] intentFilterArray = null;
        IBinder iBinder = null;
        block7: while (parcel.dataPosition() < n2) {
            int n4 = zza.zzat(parcel);
            switch (zza.zzca(n4)) {
                default: {
                    zza.zzb(parcel, n4);
                    continue block7;
                }
                case 1: {
                    n3 = zza.zzg(parcel, n4);
                    continue block7;
                }
                case 2: {
                    iBinder = zza.zzq(parcel, n4);
                    continue block7;
                }
                case 3: {
                    intentFilterArray = (IntentFilter[])zza.zzb(parcel, n4, IntentFilter.CREATOR);
                    continue block7;
                }
                case 4: {
                    string3 = zza.zzp(parcel, n4);
                    continue block7;
                }
                case 5: 
            }
            string2 = zza.zzp(parcel, n4);
        }
        if (parcel.dataPosition() != n2) {
            throw new zza.zza("Overread allowed size end=" + n2, parcel);
        }
        return new AddListenerRequest(n3, iBinder, intentFilterArray, string3, string2);
    }

    public AddListenerRequest[] zzlB(int n2) {
        return new AddListenerRequest[n2];
    }
}

