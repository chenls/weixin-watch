/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.DeadObjectException
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzj;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.api.internal.zzl;
import com.google.android.gms.common.api.internal.zzx;
import java.util.Iterator;

public class zzg
implements zzk {
    private final zzl zzahj;
    private boolean zzahk = false;

    public zzg(zzl zzl2) {
        this.zzahj = zzl2;
    }

    private <A extends Api.zzb> void zza(zzj.zze<A> zze2) throws DeadObjectException {
        this.zzahj.zzagW.zzb(zze2);
        Api.zzc<A> zzc2 = this.zzahj.zzagW.zza(zze2.zzoR());
        if (!zzc2.isConnected() && this.zzahj.zzaio.containsKey(zze2.zzoR())) {
            zze2.zzw(new Status(17));
            return;
        }
        zze2.zzb(zzc2);
    }

    @Override
    public void begin() {
    }

    @Override
    public void connect() {
        if (this.zzahk) {
            this.zzahk = false;
            this.zzahj.zza(new zzl.zza(this){

                @Override
                public void zzpt() {
                    ((zzg)zzg.this).zzahj.zzais.zzi(null);
                }
            });
        }
    }

    @Override
    public boolean disconnect() {
        if (this.zzahk) {
            return false;
        }
        if (this.zzahj.zzagW.zzpG()) {
            this.zzahk = true;
            Iterator<zzx> iterator = this.zzahj.zzagW.zzaia.iterator();
            while (iterator.hasNext()) {
                iterator.next().zzpU();
            }
            return false;
        }
        this.zzahj.zzh(null);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int n2) {
        this.zzahj.zzh(null);
        this.zzahj.zzais.zzc(n2, this.zzahk);
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zza.zza<R, A>> T zza(T t2) {
        return this.zzb(t2);
    }

    @Override
    public void zza(ConnectionResult connectionResult, Api<?> api, int n2) {
    }

    @Override
    public <A extends Api.zzb, T extends zza.zza<? extends Result, A>> T zzb(T t2) {
        try {
            this.zza(t2);
        }
        catch (DeadObjectException deadObjectException) {
            this.zzahj.zza(new zzl.zza(this){

                @Override
                public void zzpt() {
                    zzg.this.onConnectionSuspended(1);
                }
            });
            return t2;
        }
        return t2;
    }

    void zzps() {
        if (this.zzahk) {
            this.zzahk = false;
            this.zzahj.zzagW.zzaa(false);
            this.disconnect();
        }
    }
}

