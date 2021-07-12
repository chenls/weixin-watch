/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.zzl;
import com.google.android.gms.common.internal.zzx;

public class zzc
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    public final Api<?> zzagT;
    private final int zzagU;
    private zzl zzagV;

    public zzc(Api<?> api, int n2) {
        this.zzagT = api;
        this.zzagU = n2;
    }

    private void zzpi() {
        zzx.zzb(this.zzagV, (Object)"Callbacks must be attached to a GoogleApiClient instance before connecting the client.");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        this.zzpi();
        this.zzagV.onConnected(bundle);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzpi();
        this.zzagV.zza(connectionResult, this.zzagT, this.zzagU);
    }

    @Override
    public void onConnectionSuspended(int n2) {
        this.zzpi();
        this.zzagV.onConnectionSuspended(n2);
    }

    public void zza(zzl zzl2) {
        this.zzagV = zzl2;
    }
}

