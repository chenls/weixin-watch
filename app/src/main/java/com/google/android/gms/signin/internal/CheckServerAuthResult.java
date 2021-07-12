/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.signin.internal.zzc;
import java.util.List;

public class CheckServerAuthResult
implements SafeParcelable {
    public static final Parcelable.Creator<CheckServerAuthResult> CREATOR = new zzc();
    final int mVersionCode;
    final boolean zzbhf;
    final List<Scope> zzbhg;

    CheckServerAuthResult(int n2, boolean bl2, List<Scope> list) {
        this.mVersionCode = n2;
        this.zzbhf = bl2;
        this.zzbhg = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzc.zza(this, parcel, n2);
    }
}

