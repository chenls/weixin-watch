/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.internal.zzme;
import com.google.android.gms.internal.zzmg;
import com.google.android.gms.internal.zzmi;
import com.google.android.gms.internal.zzmj;
import com.google.android.gms.internal.zzml;

public final class zzmh
implements zzmg {
    @Override
    public PendingResult<Status> zzf(GoogleApiClient googleApiClient) {
        return googleApiClient.zzb(new zzmi.zza(googleApiClient){

            @Override
            protected void zza(zzmj zzmj2) throws RemoteException {
                ((zzml)zzmj2.zzqJ()).zza(new zza(this));
            }
        });
    }

    private static class zza
    extends zzme {
        private final zza.zzb<Status> zzamC;

        public zza(zza.zzb<Status> zzb2) {
            this.zzamC = zzb2;
        }

        @Override
        public void zzcb(int n2) throws RemoteException {
            this.zzamC.zzs(new Status(n2));
        }
    }
}

