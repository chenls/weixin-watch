/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzqu;
import com.google.android.gms.playlog.internal.zzf;

public class zzd
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private final zzqu.zza zzbdJ;
    private boolean zzbdK;
    private zzf zzbdy;

    public zzd(zzqu.zza zza2) {
        this.zzbdJ = zza2;
        this.zzbdy = null;
        this.zzbdK = true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        this.zzbdy.zzau(false);
        if (this.zzbdK && this.zzbdJ != null) {
            this.zzbdJ.zzES();
        }
        this.zzbdK = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzbdy.zzau(true);
        if (this.zzbdK && this.zzbdJ != null) {
            if (connectionResult.hasResolution()) {
                this.zzbdJ.zzc(connectionResult.getResolution());
            } else {
                this.zzbdJ.zzET();
            }
        }
        this.zzbdK = false;
    }

    @Override
    public void onConnectionSuspended(int n2) {
        this.zzbdy.zzau(true);
    }

    public void zza(zzf zzf2) {
        this.zzbdy = zzf2;
    }

    public void zzat(boolean bl2) {
        this.zzbdK = bl2;
    }
}

