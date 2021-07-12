/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzz;

public class ResolveAccountResponse
implements SafeParcelable {
    public static final Parcelable.Creator<ResolveAccountResponse> CREATOR = new zzz();
    final int mVersionCode;
    private boolean zzahx;
    IBinder zzakA;
    private ConnectionResult zzams;
    private boolean zzamt;

    ResolveAccountResponse(int n2, IBinder iBinder, ConnectionResult connectionResult, boolean bl2, boolean bl3) {
        this.mVersionCode = n2;
        this.zzakA = iBinder;
        this.zzams = connectionResult;
        this.zzahx = bl2;
        this.zzamt = bl3;
    }

    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof ResolveAccountResponse)) {
                    return false;
                }
                object = (ResolveAccountResponse)object;
                if (!this.zzams.equals(((ResolveAccountResponse)object).zzams) || !this.zzqX().equals(((ResolveAccountResponse)object).zzqX())) break block5;
            }
            return true;
        }
        return false;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzz.zza(this, parcel, n2);
    }

    public zzp zzqX() {
        return zzp.zza.zzaP(this.zzakA);
    }

    public ConnectionResult zzqY() {
        return this.zzams;
    }

    public boolean zzqZ() {
        return this.zzahx;
    }

    public boolean zzra() {
        return this.zzamt;
    }
}

