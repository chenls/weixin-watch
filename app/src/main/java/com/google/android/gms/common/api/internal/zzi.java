/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.api.internal.zzl;
import java.util.Collections;

public class zzi
implements zzk {
    private final zzl zzahj;

    public zzi(zzl zzl2) {
        this.zzahj = zzl2;
    }

    @Override
    public void begin() {
        this.zzahj.zzpM();
        this.zzahj.zzagW.zzahU = Collections.emptySet();
    }

    @Override
    public void connect() {
        this.zzahj.zzpK();
    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int n2) {
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zza.zza<R, A>> T zza(T t2) {
        this.zzahj.zzagW.zzahN.add(t2);
        return t2;
    }

    @Override
    public void zza(ConnectionResult connectionResult, Api<?> api, int n2) {
    }

    @Override
    public <A extends Api.zzb, T extends zza.zza<? extends Result, A>> T zzb(T t2) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}

