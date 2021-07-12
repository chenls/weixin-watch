/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzg;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.api.internal.zzi;
import com.google.android.gms.common.api.internal.zzj;
import com.google.android.gms.common.api.internal.zzk;
import com.google.android.gms.common.api.internal.zzp;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzro;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class zzl
implements zzp {
    private final Context mContext;
    private final Lock zzXG;
    final zzj zzagW;
    private final zzc zzags;
    final Api.zza<? extends zzrn, zzro> zzagt;
    final Map<Api<?>, Integer> zzahA;
    final Map<Api.zzc<?>, Api.zzb> zzahT;
    final zzf zzahz;
    private final Condition zzaim;
    private final zzb zzain;
    final Map<Api.zzc<?>, ConnectionResult> zzaio = new HashMap();
    private volatile zzk zzaip;
    private ConnectionResult zzaiq = null;
    int zzair;
    final zzp.zza zzais;

    public zzl(Context object, zzj zzj2, Lock lock, Looper looper, zzc zzc2, Map<Api.zzc<?>, Api.zzb> map, zzf zzf2, Map<Api<?>, Integer> map2, Api.zza<? extends zzrn, zzro> zza2, ArrayList<com.google.android.gms.common.api.internal.zzc> arrayList, zzp.zza zza3) {
        this.mContext = object;
        this.zzXG = lock;
        this.zzags = zzc2;
        this.zzahT = map;
        this.zzahz = zzf2;
        this.zzahA = map2;
        this.zzagt = zza2;
        this.zzagW = zzj2;
        this.zzais = zza3;
        object = arrayList.iterator();
        while (object.hasNext()) {
            ((com.google.android.gms.common.api.internal.zzc)object.next()).zza(this);
        }
        this.zzain = new zzb(looper);
        this.zzaim = lock.newCondition();
        this.zzaip = new zzi(this);
    }

    @Override
    public ConnectionResult blockingConnect() {
        this.connect();
        while (this.isConnecting()) {
            try {
                this.zzaim.await();
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (this.isConnected()) {
            return ConnectionResult.zzafB;
        }
        if (this.zzaiq != null) {
            return this.zzaiq;
        }
        return new ConnectionResult(13, null);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public ConnectionResult blockingConnect(long var1_1, TimeUnit var3_2) {
        this.connect();
        var1_1 = var3_2.toNanos(var1_1);
        while (this.isConnecting()) {
            if (var1_1 > 0L) ** GOTO lbl9
            try {
                this.disconnect();
                return new ConnectionResult(14, null);
lbl9:
                // 1 sources

                var1_1 = this.zzaim.awaitNanos(var1_1);
            }
            catch (InterruptedException var3_3) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (this.isConnected()) {
            return ConnectionResult.zzafB;
        }
        if (this.zzaiq != null) {
            return this.zzaiq;
        }
        return new ConnectionResult(13, null);
    }

    @Override
    public void connect() {
        this.zzaip.connect();
    }

    @Override
    public boolean disconnect() {
        boolean bl2 = this.zzaip.disconnect();
        if (bl2) {
            this.zzaio.clear();
        }
        return bl2;
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        String string3 = string2 + "  ";
        for (Api<?> api : this.zzahA.keySet()) {
            printWriter.append(string2).append(api.getName()).println(":");
            this.zzahT.get(api.zzoR()).dump(string3, fileDescriptor, printWriter, stringArray);
        }
    }

    @Override
    @Nullable
    public ConnectionResult getConnectionResult(@NonNull Api<?> object) {
        if (this.zzahT.containsKey(object = ((Api)object).zzoR())) {
            if (this.zzahT.get(object).isConnected()) {
                return ConnectionResult.zzafB;
            }
            if (this.zzaio.containsKey(object)) {
                return this.zzaio.get(object);
            }
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        return this.zzaip instanceof zzg;
    }

    @Override
    public boolean isConnecting() {
        return this.zzaip instanceof zzh;
    }

    public void onConnected(@Nullable Bundle bundle) {
        this.zzXG.lock();
        try {
            this.zzaip.onConnected(bundle);
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    public void onConnectionSuspended(int n2) {
        this.zzXG.lock();
        try {
            this.zzaip.onConnectionSuspended(n2);
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zza.zza<R, A>> T zza(@NonNull T t2) {
        return this.zzaip.zza(t2);
    }

    public void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, int n2) {
        this.zzXG.lock();
        try {
            this.zzaip.zza(connectionResult, api, n2);
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    void zza(zza zza2) {
        zza2 = this.zzain.obtainMessage(1, zza2);
        this.zzain.sendMessage((Message)zza2);
    }

    void zza(RuntimeException runtimeException) {
        runtimeException = this.zzain.obtainMessage(2, runtimeException);
        this.zzain.sendMessage((Message)runtimeException);
    }

    @Override
    public boolean zza(zzu zzu2) {
        return false;
    }

    @Override
    public <A extends Api.zzb, T extends zza.zza<? extends Result, A>> T zzb(@NonNull T t2) {
        return this.zzaip.zzb(t2);
    }

    void zzh(ConnectionResult connectionResult) {
        this.zzXG.lock();
        try {
            this.zzaiq = connectionResult;
            this.zzaip = new zzi(this);
            this.zzaip.begin();
            this.zzaim.signalAll();
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public void zzoW() {
    }

    void zzpK() {
        this.zzXG.lock();
        try {
            this.zzaip = new zzh(this, this.zzahz, this.zzahA, this.zzags, this.zzagt, this.zzXG, this.mContext);
            this.zzaip.begin();
            this.zzaim.signalAll();
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    void zzpL() {
        this.zzXG.lock();
        try {
            this.zzagW.zzpF();
            this.zzaip = new zzg(this);
            this.zzaip.begin();
            this.zzaim.signalAll();
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    void zzpM() {
        Iterator<Api.zzb> iterator = this.zzahT.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().disconnect();
        }
    }

    @Override
    public void zzpj() {
        if (this.isConnected()) {
            ((zzg)this.zzaip).zzps();
        }
    }

    static abstract class zza {
        private final zzk zzait;

        protected zza(zzk zzk2) {
            this.zzait = zzk2;
        }

        public final void zzd(zzl zzl2) {
            block4: {
                zzl2.zzXG.lock();
                zzk zzk2 = zzl2.zzaip;
                zzk zzk3 = this.zzait;
                if (zzk2 == zzk3) break block4;
                zzl2.zzXG.unlock();
                return;
            }
            try {
                this.zzpt();
                return;
            }
            finally {
                zzl2.zzXG.unlock();
            }
        }

        protected abstract void zzpt();
    }

    final class zzb
    extends Handler {
        zzb(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    Log.w((String)"GACStateManager", (String)("Unknown message id: " + message.what));
                    return;
                }
                case 1: {
                    ((zza)message.obj).zzd(zzl.this);
                    return;
                }
                case 2: 
            }
            throw (RuntimeException)message.obj;
        }
    }
}

