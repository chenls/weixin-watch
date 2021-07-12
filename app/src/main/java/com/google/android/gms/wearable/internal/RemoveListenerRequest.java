/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.wearable.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.wearable.internal.zzaw;
import com.google.android.gms.wearable.internal.zzbg;

public class RemoveListenerRequest
implements SafeParcelable {
    public static final Parcelable.Creator<RemoveListenerRequest> CREATOR = new zzbg();
    final int mVersionCode;
    public final zzaw zzbrB;

    RemoveListenerRequest(int n2, IBinder iBinder) {
        this.mVersionCode = n2;
        if (iBinder != null) {
            this.zzbrB = zzaw.zza.zzet(iBinder);
            return;
        }
        this.zzbrB = null;
    }

    public RemoveListenerRequest(zzaw zzaw2) {
        this.mVersionCode = 1;
        this.zzbrB = zzaw2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzbg.zza(this, parcel, n2);
    }

    IBinder zzIy() {
        if (this.zzbrB == null) {
            return null;
        }
        return this.zzbrB.asBinder();
    }
}

