/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzl;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.api.internal.zzp;
import com.google.android.gms.common.api.internal.zzq;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.api.internal.zzv;
import com.google.android.gms.common.api.internal.zzw;
import com.google.android.gms.common.api.internal.zzx;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.internal.zzmf;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzro;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

public final class zzj
extends GoogleApiClient
implements zzp.zza {
    private final Context mContext;
    private final Lock zzXG;
    private final int zzagp;
    private final Looper zzagr;
    private final com.google.android.gms.common.zzc zzags;
    final Api.zza<? extends zzrn, zzro> zzagt;
    final Map<Api<?>, Integer> zzahA;
    private final zzk zzahL;
    private zzp zzahM = null;
    final Queue<zza.zza<?, ?>> zzahN = new LinkedList();
    private volatile boolean zzahO;
    private long zzahP = 120000L;
    private long zzahQ = 5000L;
    private final zza zzahR;
    zzc zzahS;
    final Map<Api.zzc<?>, Api.zzb> zzahT;
    Set<Scope> zzahU = new HashSet<Scope>();
    private final Set<zzq<?>> zzahV = Collections.newSetFromMap(new WeakHashMap());
    final Set<zze<?>> zzahW = Collections.newSetFromMap(new ConcurrentHashMap(16, 0.75f, 2));
    private com.google.android.gms.common.api.zza zzahX;
    private final ArrayList<com.google.android.gms.common.api.internal.zzc> zzahY;
    private Integer zzahZ = null;
    final zzf zzahz;
    Set<zzx> zzaia = null;
    private final zzd zzaib = new zzd(){

        @Override
        public void zzc(zze<?> zze2) {
            zzj.this.zzahW.remove(zze2);
            if (zze2.zzpa() != null && zzj.this.zzahX != null) {
                zzj.this.zzahX.remove(zze2.zzpa());
            }
        }
    };
    private final zzk.zza zzaic = new zzk.zza(){

        @Override
        public boolean isConnected() {
            return zzj.this.isConnected();
        }

        @Override
        public Bundle zzoi() {
            return null;
        }
    };

    /*
     * WARNING - void declaration
     */
    public zzj(Context object, Lock object22, Looper looper, zzf zzf2, com.google.android.gms.common.zzc zzc2, Api.zza<? extends zzrn, zzro> zza2, Map<Api<?>, Integer> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.zzc<?>, Api.zzb> map2, int n2, int n3, ArrayList<com.google.android.gms.common.api.internal.zzc> arrayList) {
        void var6_10;
        void var4_8;
        void var9_13;
        void var8_12;
        void var13_17;
        void var10_14;
        void var7_11;
        void var11_15;
        void var5_9;
        void var3_7;
        this.mContext = object;
        this.zzXG = object22;
        this.zzahL = new zzk((Looper)var3_7, this.zzaic);
        this.zzagr = var3_7;
        this.zzahR = new zza((Looper)var3_7);
        this.zzags = var5_9;
        this.zzagp = var11_15;
        if (this.zzagp >= 0) {
            void var12_16;
            this.zzahZ = (int)var12_16;
        }
        this.zzahA = var7_11;
        this.zzahT = var10_14;
        this.zzahY = var13_17;
        for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : var8_12) {
            this.zzahL.registerConnectionCallbacks(connectionCallbacks);
        }
        for (GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener : var9_13) {
            this.zzahL.registerConnectionFailedListener(onConnectionFailedListener);
        }
        this.zzahz = var4_8;
        this.zzagt = var6_10;
    }

    private void resume() {
        this.zzXG.lock();
        try {
            if (this.zzpB()) {
                this.zzpC();
            }
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    public static int zza(Iterable<Api.zzb> object, boolean bl2) {
        int n2 = 1;
        object = object.iterator();
        boolean bl3 = false;
        int n3 = 0;
        while (object.hasNext()) {
            Api.zzb zzb2 = (Api.zzb)object.next();
            if (zzb2.zzmE()) {
                n3 = 1;
            }
            if (!zzb2.zznb()) continue;
            bl3 = true;
        }
        if (n3 != 0) {
            n3 = n2;
            if (bl3) {
                n3 = n2;
                if (bl2) {
                    n3 = 2;
                }
            }
            return n3;
        }
        return 3;
    }

    private void zza(final GoogleApiClient googleApiClient, final zzv zzv2, final boolean bl2) {
        zzmf.zzamA.zzf(googleApiClient).setResultCallback(new ResultCallback<Status>(){

            @Override
            public /* synthetic */ void onResult(@NonNull Result result) {
                this.zzp((Status)result);
            }

            public void zzp(@NonNull Status status) {
                com.google.android.gms.auth.api.signin.internal.zzq.zzaf(zzj.this.mContext).zznr();
                if (status.isSuccess() && zzj.this.isConnected()) {
                    zzj.this.reconnect();
                }
                zzv2.zza(status);
                if (bl2) {
                    googleApiClient.disconnect();
                }
            }
        });
    }

    private static void zza(zze<?> zze2, com.google.android.gms.common.api.zza zza2, IBinder iBinder) {
        if (zze2.isReady()) {
            zze2.zza(new zzb(zze2, zza2, iBinder));
            return;
        }
        if (iBinder != null && iBinder.isBinderAlive()) {
            zzb zzb2 = new zzb(zze2, zza2, iBinder);
            zze2.zza(zzb2);
            try {
                iBinder.linkToDeath((IBinder.DeathRecipient)zzb2, 0);
                return;
            }
            catch (RemoteException remoteException) {
                zze2.cancel();
                zza2.remove(zze2.zzpa());
                return;
            }
        }
        zze2.zza(null);
        zze2.cancel();
        zza2.remove(zze2.zzpa());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzbB(int n2) {
        if (this.zzahZ == null) {
            this.zzahZ = n2;
        } else if (this.zzahZ != n2) {
            throw new IllegalStateException("Cannot use sign-in mode: " + zzj.zzbC(n2) + ". Mode was already set to " + zzj.zzbC(this.zzahZ));
        }
        if (this.zzahM != null) {
            return;
        }
        Iterator<Api.zzb> iterator = this.zzahT.values().iterator();
        n2 = 0;
        boolean bl2 = false;
        while (iterator.hasNext()) {
            Api.zzb zzb2 = iterator.next();
            if (zzb2.zzmE()) {
                bl2 = true;
            }
            if (!zzb2.zznb()) continue;
            n2 = 1;
        }
        switch (this.zzahZ) {
            case 1: {
                if (!bl2) {
                    throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                }
                if (n2 != 0) {
                    throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
            }
            default: {
                break;
            }
            case 2: {
                if (!bl2) break;
                this.zzahM = new com.google.android.gms.common.api.internal.zzd(this.mContext, this, this.zzXG, this.zzagr, this.zzags, this.zzahT, this.zzahz, this.zzahA, this.zzagt, this.zzahY);
                return;
            }
        }
        this.zzahM = new zzl(this.mContext, this, this.zzXG, this.zzagr, this.zzags, this.zzahT, this.zzahz, this.zzahA, this.zzagt, this.zzahY, this);
    }

    static String zzbC(int n2) {
        switch (n2) {
            default: {
                return "UNKNOWN";
            }
            case 3: {
                return "SIGN_IN_MODE_NONE";
            }
            case 1: {
                return "SIGN_IN_MODE_REQUIRED";
            }
            case 2: 
        }
        return "SIGN_IN_MODE_OPTIONAL";
    }

    private void zzpC() {
        this.zzahL.zzqR();
        this.zzahM.connect();
    }

    private void zzpD() {
        this.zzXG.lock();
        try {
            if (this.zzpF()) {
                this.zzpC();
            }
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ConnectionResult blockingConnect() {
        boolean bl2 = true;
        boolean bl3 = Looper.myLooper() != Looper.getMainLooper();
        com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"blockingConnect must not be called on the UI thread");
        this.zzXG.lock();
        try {
            block8: {
                if (this.zzagp >= 0) {
                    bl3 = this.zzahZ != null ? bl2 : false;
                    com.google.android.gms.common.internal.zzx.zza(bl3, (Object)"Sign-in mode should have been set explicitly by auto-manage.");
                } else if (this.zzahZ == null) {
                    this.zzahZ = zzj.zza(this.zzahT.values(), false);
                } else {
                    if (this.zzahZ != 2) break block8;
                    throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
            }
            this.zzbB(this.zzahZ);
            this.zzahL.zzqR();
            ConnectionResult connectionResult = this.zzahM.blockingConnect();
            this.zzXG.unlock();
            return connectionResult;
        }
        catch (Throwable throwable) {
            this.zzXG.unlock();
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ConnectionResult blockingConnect(long l2, @NonNull TimeUnit object) {
        boolean bl2 = false;
        if (Looper.myLooper() != Looper.getMainLooper()) {
            bl2 = true;
        }
        com.google.android.gms.common.internal.zzx.zza(bl2, (Object)"blockingConnect must not be called on the UI thread");
        com.google.android.gms.common.internal.zzx.zzb(object, (Object)"TimeUnit must not be null");
        this.zzXG.lock();
        try {
            if (this.zzahZ == null) {
                this.zzahZ = zzj.zza(this.zzahT.values(), false);
            } else if (this.zzahZ == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            this.zzbB(this.zzahZ);
            this.zzahL.zzqR();
            ConnectionResult connectionResult = this.zzahM.blockingConnect(l2, (TimeUnit)((Object)object));
            return connectionResult;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        com.google.android.gms.common.internal.zzx.zza(this.isConnected(), (Object)"GoogleApiClient is not connected yet.");
        boolean bl2 = this.zzahZ != 2;
        com.google.android.gms.common.internal.zzx.zza(bl2, (Object)"Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        final zzv zzv2 = new zzv(this);
        if (this.zzahT.containsKey(zzmf.zzUI)) {
            this.zza(this, zzv2, false);
            return zzv2;
        }
        final AtomicReference<Object> atomicReference = new AtomicReference<Object>();
        Object object = new GoogleApiClient.ConnectionCallbacks(){

            @Override
            public void onConnected(Bundle bundle) {
                zzj.this.zza((GoogleApiClient)atomicReference.get(), zzv2, true);
            }

            @Override
            public void onConnectionSuspended(int n2) {
            }
        };
        GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = new GoogleApiClient.OnConnectionFailedListener(){

            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                zzv2.zza(new Status(8));
            }
        };
        object = new GoogleApiClient.Builder(this.mContext).addApi(zzmf.API).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)object).addOnConnectionFailedListener(onConnectionFailedListener).setHandler(this.zzahR).build();
        atomicReference.set(object);
        ((GoogleApiClient)object).connect();
        return zzv2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void connect() {
        boolean bl2 = false;
        this.zzXG.lock();
        try {
            if (this.zzagp >= 0) {
                if (this.zzahZ != null) {
                    bl2 = true;
                }
                com.google.android.gms.common.internal.zzx.zza(bl2, (Object)"Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zzahZ == null) {
                this.zzahZ = zzj.zza(this.zzahT.values(), false);
            } else if (this.zzahZ == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            this.connect(this.zzahZ);
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void connect(int n2) {
        boolean bl2 = true;
        this.zzXG.lock();
        boolean bl3 = bl2;
        if (n2 != 3) {
            bl3 = bl2;
            if (n2 != 1) {
                bl3 = n2 == 2 ? bl2 : false;
            }
        }
        try {
            com.google.android.gms.common.internal.zzx.zzb(bl3, (Object)("Illegal sign-in mode: " + n2));
            this.zzbB(n2);
            this.zzpC();
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        this.zzXG.lock();
        try {
            boolean bl2 = this.zzahM != null && !this.zzahM.disconnect();
            this.zzaa(bl2);
            Object object = this.zzahV.iterator();
            while (object.hasNext()) {
                object.next().clear();
            }
            this.zzahV.clear();
            for (zze zze2 : this.zzahN) {
                zze2.zza(null);
                zze2.cancel();
            }
            this.zzahN.clear();
            object = this.zzahM;
            if (object == null) {
                this.zzXG.unlock();
                return;
            }
            this.zzpF();
            this.zzahL.zzqQ();
            this.zzXG.unlock();
            return;
        }
        catch (Throwable throwable) {
            this.zzXG.unlock();
            throw throwable;
        }
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        printWriter.append(string2).append("mContext=").println(this.mContext);
        printWriter.append(string2).append("mResuming=").print(this.zzahO);
        printWriter.append(" mWorkQueue.size()=").print(this.zzahN.size());
        printWriter.append(" mUnconsumedRunners.size()=").println(this.zzahW.size());
        if (this.zzahM != null) {
            this.zzahM.dump(string2, fileDescriptor, printWriter, stringArray);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    @NonNull
    public ConnectionResult getConnectionResult(@NonNull Api<?> object) {
        this.zzXG.lock();
        try {
            if (!this.isConnected() && !this.zzpB()) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            }
            if (!this.zzahT.containsKey(((Api)object).zzoR())) throw new IllegalArgumentException(((Api)object).getName() + " was never registered with GoogleApiClient");
            ConnectionResult connectionResult = this.zzahM.getConnectionResult((Api<?>)object);
            if (connectionResult != null) return connectionResult;
            if (this.zzpB()) {
                object = ConnectionResult.zzafB;
                return object;
            }
            Log.i((String)"GoogleApiClientImpl", (String)this.zzpH());
            Log.wtf((String)"GoogleApiClientImpl", (String)(((Api)object).getName() + " requested in getConnectionResult" + " is not connected but is not present in the failed " + " connections map"), (Throwable)new Exception());
            object = new ConnectionResult(8, null);
            return object;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public Context getContext() {
        return this.mContext;
    }

    @Override
    public Looper getLooper() {
        return this.zzagr;
    }

    public int getSessionId() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean hasConnectedApi(@NonNull Api<?> object) {
        return (object = this.zzahT.get(((Api)object).zzoR())) != null && object.isConnected();
    }

    @Override
    public boolean isConnected() {
        return this.zzahM != null && this.zzahM.isConnected();
    }

    @Override
    public boolean isConnecting() {
        return this.zzahM != null && this.zzahM.isConnecting();
    }

    @Override
    public boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zzahL.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Override
    public boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zzahL.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Override
    public void reconnect() {
        this.disconnect();
        this.connect();
    }

    @Override
    public void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzahL.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzahL.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override
    public void stopAutoManage(final @NonNull FragmentActivity fragmentActivity) {
        if (this.zzagp >= 0) {
            zzw zzw2 = zzw.zza(fragmentActivity);
            if (zzw2 == null) {
                new Handler(this.mContext.getMainLooper()).post(new Runnable(){

                    @Override
                    public void run() {
                        if (fragmentActivity.isFinishing() || fragmentActivity.getSupportFragmentManager().isDestroyed()) {
                            return;
                        }
                        zzw.zzb(fragmentActivity).zzbD(zzj.this.zzagp);
                    }
                });
                return;
            }
            zzw2.zzbD(this.zzagp);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    @Override
    public void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zzahL.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zzahL.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @Override
    @NonNull
    public <C extends Api.zzb> C zza(@NonNull Api.zzc<C> object) {
        object = this.zzahT.get(object);
        com.google.android.gms.common.internal.zzx.zzb(object, (Object)"Appropriate Api was not requested.");
        return (C)object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <A extends Api.zzb, R extends Result, T extends zza.zza<R, A>> T zza(@NonNull T t2) {
        boolean bl2 = t2.zzoR() != null;
        com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"This task can not be enqueued (it's probably a Batch or malformed)");
        com.google.android.gms.common.internal.zzx.zzb(this.zzahT.containsKey(t2.zzoR()), (Object)"GoogleApiClient is not configured to use the API required for this call.");
        this.zzXG.lock();
        try {
            if (this.zzahM == null) {
                this.zzahN.add(t2);
                return t2;
            }
            t2 = this.zzahM.zza(t2);
            return t2;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public void zza(zzx zzx2) {
        this.zzXG.lock();
        try {
            if (this.zzaia == null) {
                this.zzaia = new HashSet<zzx>();
            }
            this.zzaia.add(zzx2);
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public boolean zza(@NonNull Api<?> api) {
        return this.zzahT.containsKey(api.zzoR());
    }

    @Override
    public boolean zza(zzu zzu2) {
        return this.zzahM != null && this.zzahM.zza(zzu2);
    }

    void zzaa(boolean bl2) {
        for (zze<?> zze2 : this.zzahW) {
            if (zze2.zzpa() == null) {
                if (bl2) {
                    zze2.zzpg();
                    continue;
                }
                zze2.cancel();
                this.zzahW.remove(zze2);
                continue;
            }
            zze2.zzpe();
            IBinder iBinder = this.zza((zza.zza)((Object)zze2.zzoR())).zzoT();
            zzj.zza(zze2, this.zzahX, iBinder);
            this.zzahW.remove(zze2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <A extends Api.zzb, T extends zza.zza<? extends Result, A>> T zzb(@NonNull T t2) {
        boolean bl2 = t2.zzoR() != null;
        com.google.android.gms.common.internal.zzx.zzb(bl2, (Object)"This task can not be executed (it's probably a Batch or malformed)");
        this.zzXG.lock();
        try {
            if (this.zzahM == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (this.zzpB()) {
                this.zzahN.add(t2);
                while (!this.zzahN.isEmpty()) {
                    zze zze2 = this.zzahN.remove();
                    this.zzb(zze2);
                    zze2.zzw(Status.zzagE);
                }
                return t2;
            }
            t2 = this.zzahM.zzb(t2);
            return t2;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    <A extends Api.zzb> void zzb(zze<A> zze2) {
        this.zzahW.add(zze2);
        zze2.zza(this.zzaib);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void zzb(zzx zzx2) {
        this.zzXG.lock();
        try {
            if (this.zzaia == null) {
                Log.wtf((String)"GoogleApiClientImpl", (String)"Attempted to remove pending transform when no transforms are registered.", (Throwable)new Exception());
                return;
            }
            if (!this.zzaia.remove(zzx2)) {
                Log.wtf((String)"GoogleApiClientImpl", (String)"Failed to remove pending transform - this may lead to memory leaks!", (Throwable)new Exception());
                return;
            }
            if (this.zzpG()) return;
            this.zzahM.zzpj();
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public void zzc(int n2, boolean bl2) {
        if (n2 == 1 && !bl2) {
            this.zzpE();
        }
        for (zze<?> zze2 : this.zzahW) {
            if (bl2) {
                zze2.zzpe();
            }
            zze2.zzx(new Status(8, "The connection to Google Play services was lost"));
        }
        this.zzahW.clear();
        this.zzahL.zzbT(n2);
        this.zzahL.zzqQ();
        if (n2 == 2) {
            this.zzpC();
        }
    }

    @Override
    public void zzd(ConnectionResult connectionResult) {
        if (!this.zzags.zzd(this.mContext, connectionResult.getErrorCode())) {
            this.zzpF();
        }
        if (!this.zzpB()) {
            this.zzahL.zzk(connectionResult);
            this.zzahL.zzqQ();
        }
    }

    @Override
    public void zzi(Bundle bundle) {
        while (!this.zzahN.isEmpty()) {
            this.zzb(this.zzahN.remove());
        }
        this.zzahL.zzk(bundle);
    }

    @Override
    public void zzoW() {
        if (this.zzahM != null) {
            this.zzahM.zzoW();
        }
    }

    boolean zzpB() {
        return this.zzahO;
    }

    void zzpE() {
        if (this.zzpB()) {
            return;
        }
        this.zzahO = true;
        if (this.zzahS == null) {
            this.zzahS = zzn.zza(this.mContext.getApplicationContext(), new zzc(this), this.zzags);
        }
        this.zzahR.sendMessageDelayed(this.zzahR.obtainMessage(1), this.zzahP);
        this.zzahR.sendMessageDelayed(this.zzahR.obtainMessage(2), this.zzahQ);
    }

    boolean zzpF() {
        if (!this.zzpB()) {
            return false;
        }
        this.zzahO = false;
        this.zzahR.removeMessages(2);
        this.zzahR.removeMessages(1);
        if (this.zzahS != null) {
            this.zzahS.unregister();
            this.zzahS = null;
        }
        return true;
    }

    boolean zzpG() {
        boolean bl2;
        block4: {
            bl2 = false;
            this.zzXG.lock();
            try {
                Set<zzx> set = this.zzaia;
                if (set != null) break block4;
                this.zzXG.unlock();
                return false;
            }
            catch (Throwable throwable) {
                this.zzXG.unlock();
                throw throwable;
            }
        }
        boolean bl3 = this.zzaia.isEmpty();
        if (!bl3) {
            bl2 = true;
        }
        this.zzXG.unlock();
        return bl2;
    }

    String zzpH() {
        StringWriter stringWriter = new StringWriter();
        this.dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    @Override
    public <L> zzq<L> zzr(@NonNull L object) {
        com.google.android.gms.common.internal.zzx.zzb(object, (Object)"Listener must not be null");
        this.zzXG.lock();
        try {
            object = new zzq<L>(this.zzagr, object);
            this.zzahV.add((zzq<?>)object);
            return object;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    final class zza
    extends Handler {
        zza(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    Log.w((String)"GoogleApiClientImpl", (String)("Unknown message id: " + message.what));
                    return;
                }
                case 1: {
                    zzj.this.zzpD();
                    return;
                }
                case 2: 
            }
            zzj.this.resume();
        }
    }

    private static class zzb
    implements IBinder.DeathRecipient,
    zzd {
        private final WeakReference<zze<?>> zzaii;
        private final WeakReference<com.google.android.gms.common.api.zza> zzaij;
        private final WeakReference<IBinder> zzaik;

        private zzb(zze zze2, com.google.android.gms.common.api.zza zza2, IBinder iBinder) {
            this.zzaij = new WeakReference<com.google.android.gms.common.api.zza>(zza2);
            this.zzaii = new WeakReference<zze>(zze2);
            this.zzaik = new WeakReference<IBinder>(iBinder);
        }

        private void zzpI() {
            zze zze2 = (zze)this.zzaii.get();
            com.google.android.gms.common.api.zza zza2 = (com.google.android.gms.common.api.zza)this.zzaij.get();
            if (zza2 != null && zze2 != null) {
                zza2.remove(zze2.zzpa());
            }
            zze2 = (IBinder)this.zzaik.get();
            if (this.zzaik != null) {
                zze2.unlinkToDeath(this, 0);
            }
        }

        public void binderDied() {
            this.zzpI();
        }

        @Override
        public void zzc(zze<?> zze2) {
            this.zzpI();
        }
    }

    static class zzc
    extends zzn {
        private WeakReference<zzj> zzail;

        zzc(zzj zzj2) {
            this.zzail = new WeakReference<zzj>(zzj2);
        }

        @Override
        public void zzpJ() {
            zzj zzj2 = (zzj)this.zzail.get();
            if (zzj2 == null) {
                return;
            }
            zzj2.resume();
        }
    }

    static interface zzd {
        public void zzc(zze<?> var1);
    }

    static interface zze<A extends Api.zzb> {
        public void cancel();

        public boolean isReady();

        public void zza(zzd var1);

        public void zzb(A var1) throws DeadObjectException;

        public Api.zzc<A> zzoR();

        public Integer zzpa();

        public void zzpe();

        public void zzpg();

        public void zzw(Status var1);

        public void zzx(Status var1);
    }
}

