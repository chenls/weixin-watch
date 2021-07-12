/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.IntentFilter
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzbq;
import com.google.android.gms.wearable.internal.zzc;

public class AddListenerRequest
implements SafeParcelable {
    public static final Parcelable.Creator<AddListenerRequest> CREATOR = new zzc();
    final int mVersionCode;
    public final zzaw zzbrB;
    public final IntentFilter[] zzbrC;
    public final String zzbrD;
    public final String zzbrE;

    /*
     * Enabled aggressive block sorting
     */
    AddListenerRequest(int n2, IBinder iBinder, IntentFilter[] intentFilterArray, String string2, String string3) {
        this.mVersionCode = n2;
        this.zzbrB = iBinder != null ? zzaw.zza.zzet(iBinder) : null;
        this.zzbrC = intentFilterArray;
        this.zzbrD = string2;
        this.zzbrE = string3;
    }

    public AddListenerRequest(zzbq zzbq2) {
        this.mVersionCode = 1;
        this.zzbrB = zzbq2;
        this.zzbrC = zzbq2.zzIO();
        this.zzbrD = zzbq2.zzIP();
        this.zzbrE = null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzc.zza(this, parcel, n2);
    }

    IBinder zzIy() {
        if (this.zzbrB == null) {
            return null;
        }
        return this.zzbrB.asBinder();
    }
}

