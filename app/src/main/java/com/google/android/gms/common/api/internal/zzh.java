/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.api.internal.zzl;
import com.google.android.gms.common.api.internal.zzm;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzro;
import com.google.android.gms.signin.internal.SignInResponse;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

public class zzh
implements zzk {
    private final Context mContext;
    private final Lock zzXG;
    private final com.google.android.gms.common.zzc zzags;
    private final Api.zza<? extends zzrn, zzro> zzagt;
    private final Map<Api<?>, Integer> zzahA;
    private ArrayList<Future<?>> zzahB;
    private final zzl zzahj;
    private ConnectionResult zzahm;
    private int zzahn;
    private int zzaho = 0;
    private int zzahp;
    private final Bundle zzahq = new Bundle();
    private final Set<Api.zzc> zzahr = new HashSet<Api.zzc>();
    private zzrn zzahs;
    private int zzaht;
    private boolean zzahu;
    private boolean zzahv;
    private zzp zzahw;
    private boolean zzahx;
    private boolean zzahy;
    private final com.google.android.gms.common.internal.zzf zzahz;

    public zzh(zzl zzl2, com.google.android.gms.common.internal.zzf zzf2, Map<Api<?>, Integer> map, com.google.android.gms.common.zzc zzc2, Api.zza<? extends zzrn, zzro> zza2, Lock lock, Context context) {
        this.zzahB = new ArrayList();
        this.zzahj = zzl2;
        this.zzahz = zzf2;
        this.zzahA = map;
        this.zzags = zzc2;
        this.zzagt = zza2;
        this.zzXG = lock;
        this.mContext = context;
    }

    private void zzZ(boolean bl2) {
        if (this.zzahs != null) {
            if (this.zzahs.isConnected() && bl2) {
                this.zzahs.zzFG();
            }
            this.zzahs.disconnect();
            this.zzahw = null;
        }
    }

    private void zza(SignInResponse safeParcelable) {
        if (!this.zzbz(0)) {
            return;
        }
        ConnectionResult connectionResult = ((SignInResponse)safeParcelable).zzqY();
        if (connectionResult.isSuccess()) {
            connectionResult = ((ResolveAccountResponse)(safeParcelable = ((SignInResponse)safeParcelable).zzFP())).zzqY();
            if (!connectionResult.isSuccess()) {
                Log.wtf((String)"GoogleApiClientConnecting", (String)("Sign-in succeeded with resolve account failure: " + connectionResult), (Throwable)new Exception());
                this.zzg(connectionResult);
                return;
            }
            this.zzahv = true;
            this.zzahw = ((ResolveAccountResponse)safeParcelable).zzqX();
            this.zzahx = ((ResolveAccountResponse)safeParcelable).zzqZ();
            this.zzahy = ((ResolveAccountResponse)safeParcelable).zzra();
            this.zzpv();
            return;
        }
        if (this.zzf(connectionResult)) {
            this.zzpy();
            this.zzpv();
            return;
        }
        this.zzg(connectionResult);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean zza(int n2, int n3, ConnectionResult connectionResult) {
        return (n3 != 1 || this.zze(connectionResult)) && (this.zzahm == null || n2 < this.zzahn);
    }

    private void zzb(ConnectionResult connectionResult, Api<?> api, int n2) {
        int n3;
        if (n2 != 2 && this.zza(n3 = api.zzoP().getPriority(), n2, connectionResult)) {
            this.zzahm = connectionResult;
            this.zzahn = n3;
        }
        this.zzahj.zzaio.put(api.zzoR(), connectionResult);
    }

    private String zzbA(int n2) {
        switch (n2) {
            default: {
                return "UNKNOWN";
            }
            case 0: {
                return "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
            }
            case 1: 
        }
        return "STEP_GETTING_REMOTE_SERVICE";
    }

    private boolean zzbz(int n2) {
        if (this.zzaho != n2) {
            Log.i((String)"GoogleApiClientConnecting", (String)this.zzahj.zzagW.zzpH());
            Log.wtf((String)"GoogleApiClientConnecting", (String)("GoogleApiClient connecting is in step " + this.zzbA(this.zzaho) + " but received callback for step " + this.zzbA(n2)), (Throwable)new Exception());
            this.zzg(new ConnectionResult(8, null));
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean zze(ConnectionResult connectionResult) {
        return connectionResult.hasResolution() || this.zzags.zzbu(connectionResult.getErrorCode()) != null;
    }

    private boolean zzf(ConnectionResult connectionResult) {
        return this.zzaht == 2 || this.zzaht == 1 && !connectionResult.hasResolution();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzg(ConnectionResult connectionResult) {
        this.zzpz();
        boolean bl2 = !connectionResult.hasResolution();
        this.zzZ(bl2);
        this.zzahj.zzh(connectionResult);
        this.zzahj.zzais.zzd(connectionResult);
    }

    private Set<Scope> zzpA() {
        if (this.zzahz == null) {
            return Collections.emptySet();
        }
        HashSet<Scope> hashSet = new HashSet<Scope>(this.zzahz.zzqs());
        Map<Api<?>, zzf.zza> map = this.zzahz.zzqu();
        for (Api<?> api : map.keySet()) {
            if (this.zzahj.zzaio.containsKey(api.zzoR())) continue;
            hashSet.addAll(map.get(api).zzXf);
        }
        return hashSet;
    }

    private boolean zzpu() {
        --this.zzahp;
        if (this.zzahp > 0) {
            return false;
        }
        if (this.zzahp < 0) {
            Log.i((String)"GoogleApiClientConnecting", (String)this.zzahj.zzagW.zzpH());
            Log.wtf((String)"GoogleApiClientConnecting", (String)"GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", (Throwable)new Exception());
            this.zzg(new ConnectionResult(8, null));
            return false;
        }
        if (this.zzahm != null) {
            this.zzahj.zzair = this.zzahn;
            this.zzg(this.zzahm);
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzpv() {
        if (this.zzahp != 0 || this.zzahu && !this.zzahv) {
            return;
        }
        this.zzpw();
    }

    private void zzpw() {
        ArrayList<Api.zzb> arrayList = new ArrayList<Api.zzb>();
        this.zzaho = 1;
        this.zzahp = this.zzahj.zzahT.size();
        for (Api.zzc<?> zzc2 : this.zzahj.zzahT.keySet()) {
            if (this.zzahj.zzaio.containsKey(zzc2)) {
                if (!this.zzpu()) continue;
                this.zzpx();
                continue;
            }
            arrayList.add(this.zzahj.zzahT.get(zzc2));
        }
        if (!arrayList.isEmpty()) {
            this.zzahB.add(zzm.zzpN().submit(new zzc(arrayList)));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzpx() {
        this.zzahj.zzpL();
        zzm.zzpN().execute(new Runnable(){

            @Override
            public void run() {
                zzh.this.zzags.zzal(zzh.this.mContext);
            }
        });
        if (this.zzahs != null) {
            if (this.zzahx) {
                this.zzahs.zza(this.zzahw, this.zzahy);
            }
            this.zzZ(false);
        }
        for (Api.zzc<?> zzc2 : this.zzahj.zzaio.keySet()) {
            this.zzahj.zzahT.get(zzc2).disconnect();
        }
        Object object = this.zzahq.isEmpty() ? null : this.zzahq;
        this.zzahj.zzais.zzi((Bundle)object);
    }

    private void zzpy() {
        this.zzahu = false;
        this.zzahj.zzagW.zzahU = Collections.emptySet();
        for (Api.zzc zzc2 : this.zzahr) {
            if (this.zzahj.zzaio.containsKey(zzc2)) continue;
            this.zzahj.zzaio.put(zzc2, new ConnectionResult(17, null));
        }
    }

    private void zzpz() {
        Iterator<Future<?>> iterator = this.zzahB.iterator();
        while (iterator.hasNext()) {
            iterator.next().cancel(true);
        }
        this.zzahB.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void begin() {
        this.zzahj.zzaio.clear();
        this.zzahu = false;
        this.zzahm = null;
        this.zzaho = 0;
        this.zzaht = 2;
        this.zzahv = false;
        this.zzahx = false;
        HashMap<Api.zzb, GoogleApiClient.zza> hashMap = new HashMap<Api.zzb, GoogleApiClient.zza>();
        Object object = this.zzahA.keySet().iterator();
        boolean bl2 = false;
        while (object.hasNext()) {
            Api<?> api = object.next();
            Api.zzb zzb2 = this.zzahj.zzahT.get(api.zzoR());
            int n2 = this.zzahA.get(api);
            boolean bl3 = api.zzoP().getPriority() == 1;
            if (zzb2.zzmE()) {
                this.zzahu = true;
                if (n2 < this.zzaht) {
                    this.zzaht = n2;
                }
                if (n2 != 0) {
                    this.zzahr.add(api.zzoR());
                }
            }
            hashMap.put(zzb2, new zza(this, api, n2));
            bl2 = bl3 | bl2;
        }
        if (bl2) {
            this.zzahu = false;
        }
        if (this.zzahu) {
            this.zzahz.zza(this.zzahj.zzagW.getSessionId());
            object = new zze();
            this.zzahs = this.zzagt.zza(this.mContext, this.zzahj.zzagW.getLooper(), this.zzahz, this.zzahz.zzqy(), (GoogleApiClient.ConnectionCallbacks)object, (GoogleApiClient.OnConnectionFailedListener)object);
        }
        this.zzahp = this.zzahj.zzahT.size();
        this.zzahB.add(zzm.zzpN().submit(new zzb(hashMap)));
    }

    @Override
    public void connect() {
    }

    @Override
    public boolean disconnect() {
        this.zzpz();
        this.zzZ(true);
        this.zzahj.zzh(null);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onConnected(Bundle bundle) {
        block5: {
            block4: {
                if (!this.zzbz(1)) break block4;
                if (bundle != null) {
                    this.zzahq.putAll(bundle);
                }
                if (this.zzpu()) break block5;
            }
            return;
        }
        this.zzpx();
    }

    @Override
    public void onConnectionSuspended(int n2) {
        this.zzg(new ConnectionResult(8, null));
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zza.zza<R, A>> T zza(T t2) {
        this.zzahj.zzagW.zzahN.add(t2);
        return t2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void zza(ConnectionResult connectionResult, Api<?> api, int n2) {
        block3: {
            block2: {
                if (!this.zzbz(1)) break block2;
                this.zzb(connectionResult, api, n2);
                if (this.zzpu()) break block3;
            }
            return;
        }
        this.zzpx();
    }

    @Override
    public <A extends Api.zzb, T extends zza.zza<? extends Result, A>> T zzb(T t2) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    private static class zza
    implements GoogleApiClient.zza {
        private final Api<?> zzagT;
        private final int zzagU;
        private final WeakReference<zzh> zzahD;

        public zza(zzh zzh2, Api<?> api, int n2) {
            this.zzahD = new WeakReference<zzh>(zzh2);
            this.zzagT = api;
            this.zzagU = n2;
        }

        @Override
        public void zza(@NonNull ConnectionResult connectionResult) {
            zzh zzh2;
            block8: {
                boolean bl2 = false;
                zzh2 = (zzh)this.zzahD.get();
                if (zzh2 == null) {
                    return;
                }
                if (Looper.myLooper() == ((zzh)zzh2).zzahj.zzagW.getLooper()) {
                    bl2 = true;
                }
                zzx.zza(bl2, (Object)"onReportServiceBinding must be called on the GoogleApiClient handler thread");
                zzh2.zzXG.lock();
                bl2 = zzh2.zzbz(0);
                if (bl2) break block8;
                zzh2.zzXG.unlock();
                return;
            }
            try {
                if (!connectionResult.isSuccess()) {
                    zzh2.zzb(connectionResult, this.zzagT, this.zzagU);
                }
                if (zzh2.zzpu()) {
                    zzh2.zzpv();
                }
                return;
            }
            finally {
                zzh2.zzXG.unlock();
            }
        }
    }

    private class zzb
    extends zzf {
        private final Map<Api.zzb, GoogleApiClient.zza> zzahE;

        public zzb(Map<Api.zzb, GoogleApiClient.zza> map) {
            this.zzahE = map;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        @WorkerThread
        public void zzpt() {
            int n2 = zzh.this.zzags.isGooglePlayServicesAvailable(zzh.this.mContext);
            if (n2 != 0) {
                final ConnectionResult connectionResult = new ConnectionResult(n2, null);
                zzh.this.zzahj.zza(new zzl.zza(zzh.this){

                    @Override
                    public void zzpt() {
                        zzh.this.zzg(connectionResult);
                    }
                });
                return;
            } else {
                if (zzh.this.zzahu) {
                    zzh.this.zzahs.connect();
                }
                for (Api.zzb zzb2 : this.zzahE.keySet()) {
                    zzb2.zza(this.zzahE.get(zzb2));
                }
            }
        }
    }

    private class zzc
    extends zzf {
        private final ArrayList<Api.zzb> zzahH;

        public zzc(ArrayList<Api.zzb> arrayList) {
            this.zzahH = arrayList;
        }

        @Override
        @WorkerThread
        public void zzpt() {
            ((zzh)zzh.this).zzahj.zzagW.zzahU = zzh.this.zzpA();
            Iterator<Api.zzb> iterator = this.zzahH.iterator();
            while (iterator.hasNext()) {
                iterator.next().zza(zzh.this.zzahw, ((zzh)zzh.this).zzahj.zzagW.zzahU);
            }
        }
    }

    private static class zzd
    extends com.google.android.gms.signin.internal.zzb {
        private final WeakReference<zzh> zzahD;

        zzd(zzh zzh2) {
            this.zzahD = new WeakReference<zzh>(zzh2);
        }

        @Override
        @BinderThread
        public void zzb(final SignInResponse signInResponse) {
            final zzh zzh2 = (zzh)this.zzahD.get();
            if (zzh2 == null) {
                return;
            }
            zzh2.zzahj.zza(new zzl.zza(zzh2){

                @Override
                public void zzpt() {
                    zzh2.zza(signInResponse);
                }
            });
        }
    }

    private class zze
    implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {
        private zze() {
        }

        @Override
        public void onConnected(Bundle bundle) {
            zzh.this.zzahs.zza(new zzd(zzh.this));
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            zzh.this.zzXG.lock();
            try {
                if (zzh.this.zzf(connectionResult)) {
                    zzh.this.zzpy();
                    zzh.this.zzpv();
                    return;
                }
                zzh.this.zzg(connectionResult);
                return;
            }
            finally {
                zzh.this.zzXG.unlock();
            }
        }

        @Override
        public void onConnectionSuspended(int n2) {
        }
    }

    private abstract class zzf
    implements Runnable {
        private zzf() {
        }

        @Override
        @WorkerThread
        public void run() {
            block6: {
                zzh.this.zzXG.lock();
                boolean bl2 = Thread.interrupted();
                if (!bl2) break block6;
                zzh.this.zzXG.unlock();
                return;
            }
            try {
                this.zzpt();
            }
            catch (RuntimeException runtimeException) {
                zzh.this.zzahj.zza(runtimeException);
                return;
            }
            finally {
                zzh.this.zzXG.unlock();
            }
            zzh.this.zzXG.unlock();
            return;
        }

        @WorkerThread
        protected abstract void zzpt();
    }
}

