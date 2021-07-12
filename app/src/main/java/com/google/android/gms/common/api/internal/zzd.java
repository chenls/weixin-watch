/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zza;
import com.google.android.gms.common.api.internal.zzj;
import com.google.android.gms.common.api.internal.zzl;
import com.google.android.gms.common.api.internal.zzp;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.internal.zzro;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class zzd
implements zzp {
    private final Context mContext;
    private final Lock zzXG;
    private final zzj zzagW;
    private final zzl zzagX;
    private final zzl zzagY;
    private final Map<Api.zzc<?>, zzl> zzagZ = new ArrayMap();
    private final Looper zzagr;
    private final Set<zzu> zzaha = Collections.newSetFromMap(new WeakHashMap());
    private final Api.zzb zzahb;
    private Bundle zzahc;
    private ConnectionResult zzahd = null;
    private ConnectionResult zzahe = null;
    private boolean zzahf = false;
    private int zzahg = 0;

    public zzd(Context object, zzj object222, Lock lock, Looper looper, zzc zzc2, Map<Api.zzc<?>, Api.zzb> object3, zzf zzf2, Map<Api<?>, Integer> object4, Api.zza<? extends zzrn, zzro> zza2, ArrayList<com.google.android.gms.common.api.internal.zzc> object5) {
        Object object2;
        this.mContext = object;
        this.zzagW = object222;
        this.zzXG = lock;
        this.zzagr = looper;
        object222 = null;
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        for (Api.zzc<?> zzc3 : object3.keySet()) {
            object2 = (Api.zzb)object3.get(zzc3);
            if (object2.zznb()) {
                object222 = object2;
            }
            if (object2.zzmE()) {
                arrayMap.put(zzc3, (Api.zzb)object2);
                continue;
            }
            arrayMap2.put(zzc3, (Api.zzb)object2);
        }
        this.zzahb = object222;
        if (arrayMap.isEmpty()) {
            throw new IllegalStateException("CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        }
        object222 = new ArrayMap();
        object3 = new ArrayMap();
        for (Api api : object4.keySet()) {
            Api.zzc<?> zzc3;
            zzc3 = api.zzoR();
            if (arrayMap.containsKey(zzc3)) {
                object222.put(api, object4.get(api));
                continue;
            }
            if (arrayMap2.containsKey(zzc3)) {
                object3.put(api, object4.get(api));
                continue;
            }
            throw new IllegalStateException("Each API in the apiTypeMap must have a corresponding client in the clients map.");
        }
        object4 = new ArrayList<com.google.android.gms.common.api.internal.zzc>();
        object2 = new ArrayList<com.google.android.gms.common.api.internal.zzc>();
        object5 = ((ArrayList)object5).iterator();
        while (object5.hasNext()) {
            com.google.android.gms.common.api.internal.zzc zzc4 = (com.google.android.gms.common.api.internal.zzc)object5.next();
            if (object222.containsKey(zzc4.zzagT)) {
                object4.add(zzc4);
                continue;
            }
            if (object3.containsKey(zzc4.zzagT)) {
                ((ArrayList)object2).add(zzc4);
                continue;
            }
            throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the apiTypeMap");
        }
        object5 = new zzp.zza(){

            @Override
            public void zzc(int n2, boolean bl2) {
                zzd.this.zzXG.lock();
                try {
                    if (zzd.this.zzahf || zzd.this.zzahe == null || !zzd.this.zzahe.isSuccess()) {
                        zzd.zza(zzd.this, false);
                        zzd.this.zzb(n2, bl2);
                        return;
                    }
                    zzd.zza(zzd.this, true);
                    zzd.this.zzagY.onConnectionSuspended(n2);
                    return;
                }
                finally {
                    zzd.this.zzXG.unlock();
                }
            }

            @Override
            public void zzd(@NonNull ConnectionResult connectionResult) {
                zzd.this.zzXG.lock();
                try {
                    zzd.zza(zzd.this, connectionResult);
                    zzd.this.zzpm();
                    return;
                }
                finally {
                    zzd.this.zzXG.unlock();
                }
            }

            @Override
            public void zzi(@Nullable Bundle bundle) {
                zzd.this.zzXG.lock();
                try {
                    zzd.this.zzh(bundle);
                    zzd.zza(zzd.this, ConnectionResult.zzafB);
                    zzd.this.zzpm();
                    return;
                }
                finally {
                    zzd.this.zzXG.unlock();
                }
            }
        };
        this.zzagX = new zzl((Context)object, this.zzagW, lock, looper, zzc2, arrayMap2, null, (Map<Api<?>, Integer>)object3, null, (ArrayList<com.google.android.gms.common.api.internal.zzc>)object2, (zzp.zza)object5);
        object3 = new zzp.zza(){

            @Override
            public void zzc(int n2, boolean bl2) {
                zzd.this.zzXG.lock();
                try {
                    if (zzd.this.zzahf) {
                        zzd.zza(zzd.this, false);
                        zzd.this.zzb(n2, bl2);
                        return;
                    }
                    zzd.zza(zzd.this, true);
                    zzd.this.zzagX.onConnectionSuspended(n2);
                    return;
                }
                finally {
                    zzd.this.zzXG.unlock();
                }
            }

            @Override
            public void zzd(@NonNull ConnectionResult connectionResult) {
                zzd.this.zzXG.lock();
                try {
                    zzd.zzb(zzd.this, connectionResult);
                    zzd.this.zzpm();
                    return;
                }
                finally {
                    zzd.this.zzXG.unlock();
                }
            }

            @Override
            public void zzi(@Nullable Bundle bundle) {
                zzd.this.zzXG.lock();
                try {
                    zzd.zzb(zzd.this, ConnectionResult.zzafB);
                    zzd.this.zzpm();
                    return;
                }
                finally {
                    zzd.this.zzXG.unlock();
                }
            }
        };
        this.zzagY = new zzl((Context)object, this.zzagW, lock, looper, zzc2, arrayMap, zzf2, (Map<Api<?>, Integer>)object222, zza2, object4, (zzp.zza)object3);
        for (Object object222 : arrayMap2.keySet()) {
            this.zzagZ.put((Api.zzc<?>)object222, this.zzagX);
        }
        for (Object object222 : arrayMap.keySet()) {
            this.zzagZ.put((Api.zzc<?>)object222, this.zzagY);
        }
    }

    static /* synthetic */ ConnectionResult zza(zzd zzd2, ConnectionResult connectionResult) {
        zzd2.zzahd = connectionResult;
        return connectionResult;
    }

    static /* synthetic */ boolean zza(zzd zzd2, boolean bl2) {
        zzd2.zzahf = bl2;
        return bl2;
    }

    static /* synthetic */ ConnectionResult zzb(zzd zzd2, ConnectionResult connectionResult) {
        zzd2.zzahe = connectionResult;
        return connectionResult;
    }

    private void zzb(int n2, boolean bl2) {
        this.zzagW.zzc(n2, bl2);
        this.zzahe = null;
        this.zzahd = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzb(ConnectionResult connectionResult) {
        block4: {
            switch (this.zzahg) {
                default: {
                    Log.wtf((String)"CompositeGAC", (String)"Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", (Throwable)new Exception());
                    break block4;
                }
                case 2: {
                    this.zzagW.zzd(connectionResult);
                    break;
                }
                case 1: 
            }
            this.zzpo();
        }
        this.zzahg = 0;
    }

    private static boolean zzc(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    private boolean zzc(zza.zza<? extends Result, ? extends Api.zzb> object) {
        object = ((zza.zza)object).zzoR();
        zzx.zzb(this.zzagZ.containsKey(object), (Object)"GoogleApiClient is not configured to use the API required for this call.");
        return this.zzagZ.get(object).equals(this.zzagY);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzh(Bundle bundle) {
        if (this.zzahc == null) {
            this.zzahc = bundle;
            return;
        } else {
            if (bundle == null) return;
            this.zzahc.putAll(bundle);
            return;
        }
    }

    private void zzpl() {
        this.zzahe = null;
        this.zzahd = null;
        this.zzagX.connect();
        this.zzagY.connect();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzpm() {
        if (zzd.zzc(this.zzahd)) {
            if (zzd.zzc(this.zzahe) || this.zzpp()) {
                this.zzpn();
                return;
            } else {
                if (this.zzahe == null) return;
                if (this.zzahg == 1) {
                    this.zzpo();
                    return;
                }
                this.zzb(this.zzahe);
                this.zzagX.disconnect();
                return;
            }
        }
        if (this.zzahd != null && zzd.zzc(this.zzahe)) {
            this.zzagY.disconnect();
            this.zzb(this.zzahd);
            return;
        }
        if (this.zzahd == null || this.zzahe == null) return;
        ConnectionResult connectionResult = this.zzahd;
        if (this.zzagY.zzair < this.zzagX.zzair) {
            connectionResult = this.zzahe;
        }
        this.zzb(connectionResult);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzpn() {
        block4: {
            switch (this.zzahg) {
                default: {
                    Log.wtf((String)"CompositeGAC", (String)"Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", (Throwable)new Exception());
                    break block4;
                }
                case 2: {
                    this.zzagW.zzi(this.zzahc);
                    break;
                }
                case 1: 
            }
            this.zzpo();
        }
        this.zzahg = 0;
    }

    private void zzpo() {
        Iterator<zzu> iterator = this.zzaha.iterator();
        while (iterator.hasNext()) {
            iterator.next().zzna();
        }
        this.zzaha.clear();
    }

    private boolean zzpp() {
        return this.zzahe != null && this.zzahe.getErrorCode() == 4;
    }

    @Nullable
    private PendingIntent zzpq() {
        if (this.zzahb == null) {
            return null;
        }
        return PendingIntent.getActivity((Context)this.mContext, (int)this.zzagW.getSessionId(), (Intent)this.zzahb.zznc(), (int)0x8000000);
    }

    @Override
    public ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ConnectionResult blockingConnect(long l2, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void connect() {
        this.zzahg = 2;
        this.zzahf = false;
        this.zzpl();
    }

    @Override
    public boolean disconnect() {
        boolean bl2 = false;
        this.zzahe = null;
        this.zzahd = null;
        this.zzahg = 0;
        boolean bl3 = this.zzagX.disconnect();
        boolean bl4 = this.zzagY.disconnect();
        this.zzpo();
        boolean bl5 = bl2;
        if (bl3) {
            bl5 = bl2;
            if (bl4) {
                bl5 = true;
            }
        }
        return bl5;
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        printWriter.append(string2).append("authClient").println(":");
        this.zzagY.dump(string2 + "  ", fileDescriptor, printWriter, stringArray);
        printWriter.append(string2).append("anonClient").println(":");
        this.zzagX.dump(string2 + "  ", fileDescriptor, printWriter, stringArray);
    }

    @Override
    @Nullable
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        if (this.zzagZ.get(api.zzoR()).equals(this.zzagY)) {
            if (this.zzpp()) {
                return new ConnectionResult(4, this.zzpq());
            }
            return this.zzagY.getConnectionResult(api);
        }
        return this.zzagX.getConnectionResult(api);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean isConnected() {
        block5: {
            block6: {
                var3_1 = true;
                this.zzXG.lock();
                if (!this.zzagX.isConnected()) break block5;
                var2_2 = var3_1;
                if (this.zzpk()) break block6;
                var2_2 = var3_1;
                try {
                    if (this.zzpp()) break block6;
                    var1_3 = this.zzahg;
                    if (var1_3 != 1) break block5;
                    var2_2 = var3_1;
                }
                catch (Throwable var4_4) {
                    this.zzXG.unlock();
                    throw var4_4;
                }
            }
lbl15:
            // 2 sources

            while (true) {
                this.zzXG.unlock();
                return var2_2;
            }
        }
        var2_2 = false;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isConnecting() {
        this.zzXG.lock();
        try {
            int n2 = this.zzahg;
            boolean bl2 = n2 == 2;
            return bl2;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zza.zza<R, A>> T zza(@NonNull T t2) {
        if (this.zzc(t2)) {
            if (this.zzpp()) {
                t2.zzw(new Status(4, null, this.zzpq()));
                return t2;
            }
            return this.zzagY.zza(t2);
        }
        return this.zzagX.zza(t2);
    }

    @Override
    public boolean zza(zzu zzu2) {
        this.zzXG.lock();
        try {
            if ((this.isConnecting() || this.isConnected()) && !this.zzpk()) {
                this.zzaha.add(zzu2);
                if (this.zzahg == 0) {
                    this.zzahg = 1;
                }
                this.zzahe = null;
                this.zzagY.connect();
                return true;
            }
            return false;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public <A extends Api.zzb, T extends zza.zza<? extends Result, A>> T zzb(@NonNull T t2) {
        if (this.zzc(t2)) {
            if (this.zzpp()) {
                t2.zzw(new Status(4, null, this.zzpq()));
                return t2;
            }
            return this.zzagY.zzb(t2);
        }
        return this.zzagX.zzb(t2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void zzoW() {
        this.zzXG.lock();
        try {
            boolean bl2 = this.isConnecting();
            this.zzagY.disconnect();
            this.zzahe = new ConnectionResult(4);
            if (bl2) {
                new Handler(this.zzagr).post(new Runnable(){

                    @Override
                    public void run() {
                        zzd.this.zzXG.lock();
                        try {
                            zzd.this.zzpm();
                            return;
                        }
                        finally {
                            zzd.this.zzXG.unlock();
                        }
                    }
                });
                return;
            }
            this.zzpo();
            return;
        }
        finally {
            this.zzXG.unlock();
        }
    }

    @Override
    public void zzpj() {
        this.zzagX.zzpj();
        this.zzagY.zzpj();
    }

    public boolean zzpk() {
        return this.zzagY.isConnected();
    }
}

