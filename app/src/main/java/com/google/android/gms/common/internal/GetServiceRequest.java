/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zza;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.zzc;
import java.util.Collection;

public class GetServiceRequest
implements SafeParcelable {
    public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zzi();
    final int version;
    final int zzall;
    int zzalm;
    String zzaln;
    IBinder zzalo;
    Scope[] zzalp;
    Bundle zzalq;
    Account zzalr;

    public GetServiceRequest(int n2) {
        this.version = 2;
        this.zzalm = zzc.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzall = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    GetServiceRequest(int n2, int n3, int n4, String string2, IBinder iBinder, Scope[] scopeArray, Bundle bundle, Account account) {
        this.version = n2;
        this.zzall = n3;
        this.zzalm = n4;
        this.zzaln = string2;
        if (n2 < 2) {
            this.zzalr = this.zzaO(iBinder);
        } else {
            this.zzalo = iBinder;
            this.zzalr = account;
        }
        this.zzalp = scopeArray;
        this.zzalq = bundle;
    }

    private Account zzaO(IBinder iBinder) {
        Account account = null;
        if (iBinder != null) {
            account = zza.zza(zzp.zza.zzaP(iBinder));
        }
        return account;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        zzi.zza(this, parcel, n2);
    }

    public GetServiceRequest zzb(zzp zzp2) {
        if (zzp2 != null) {
            this.zzalo = zzp2.asBinder();
        }
        return this;
    }

    public GetServiceRequest zzc(Account account) {
        this.zzalr = account;
        return this;
    }

    public GetServiceRequest zzcG(String string2) {
        this.zzaln = string2;
        return this;
    }

    public GetServiceRequest zzd(Collection<Scope> collection) {
        this.zzalp = collection.toArray(new Scope[collection.size()]);
        return this;
    }

    public GetServiceRequest zzj(Bundle bundle) {
        this.zzalq = bundle;
        return this;
    }
}

