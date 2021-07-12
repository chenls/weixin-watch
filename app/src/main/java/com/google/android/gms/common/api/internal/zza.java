/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzj;
import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.atomic.AtomicReference;

public class zza {

    public static abstract class zza<R extends Result, A extends Api.zzb>
    extends com.google.android.gms.common.api.internal.zzb<R>
    implements zzb<R>,
    zzj.zze<A> {
        private final Api.zzc<A> zzaeE;
        private AtomicReference<zzj.zzd> zzagH = new AtomicReference();

        protected zza(Api.zzc<A> zzc2, GoogleApiClient googleApiClient) {
            super(zzx.zzb(googleApiClient, (Object)"GoogleApiClient must not be null"));
            this.zzaeE = zzx.zzz(zzc2);
        }

        @Override
        private void zza(RemoteException remoteException) {
            this.zzw(new Status(8, remoteException.getLocalizedMessage(), null));
        }

        @Override
        protected abstract void zza(A var1) throws RemoteException;

        @Override
        public void zza(zzj.zzd zzd2) {
            this.zzagH.set(zzd2);
        }

        @Override
        public final void zzb(A a2) throws DeadObjectException {
            try {
                this.zza(a2);
                return;
            }
            catch (DeadObjectException deadObjectException) {
                this.zza((RemoteException)((Object)deadObjectException));
                throw deadObjectException;
            }
            catch (RemoteException remoteException) {
                this.zza(remoteException);
                return;
            }
        }

        @Override
        public final Api.zzc<A> zzoR() {
            return this.zzaeE;
        }

        @Override
        public void zzpe() {
            this.setResultCallback(null);
        }

        @Override
        protected void zzpf() {
            zzj.zzd zzd2 = this.zzagH.getAndSet(null);
            if (zzd2 != null) {
                zzd2.zzc(this);
            }
        }

        @Override
        public /* synthetic */ void zzs(Object object) {
            super.zza((Result)object);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public final void zzw(Status status) {
            boolean bl2 = !status.isSuccess();
            zzx.zzb(bl2, (Object)"Failed result must not be success");
            this.zza((A)this.zzc(status));
        }
    }

    public static interface zzb<R> {
        public void zzs(R var1);

        public void zzw(Status var1);
    }
}

