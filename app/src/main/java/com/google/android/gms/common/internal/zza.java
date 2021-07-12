/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Binder
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.zze;

public class zza
extends zzp.zza {
    private Context mContext;
    private Account zzTI;
    int zzakz;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Account zza(zzp zzp2) {
        Account account = null;
        if (zzp2 == null) return account;
        long l2 = Binder.clearCallingIdentity();
        try {
            account = zzp2.getAccount();
        }
        catch (RemoteException remoteException) {
            Log.w((String)"AccountAccessor", (String)"Remote account accessor probably died");
            return null;
        }
        finally {
            Binder.restoreCallingIdentity((long)l2);
        }
        return account;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zza)) {
            return false;
        }
        return this.zzTI.equals((Object)((zza)object).zzTI);
    }

    @Override
    public Account getAccount() {
        int n2 = Binder.getCallingUid();
        if (n2 == this.zzakz) {
            return this.zzTI;
        }
        if (zze.zzf(this.mContext, n2)) {
            this.zzakz = n2;
            return this.zzTI;
        }
        throw new SecurityException("Caller is not GooglePlayServices");
    }
}

