/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzk
implements Handler.Callback {
    private final Handler mHandler;
    private final zza zzalQ;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzalR = new ArrayList();
    final ArrayList<GoogleApiClient.ConnectionCallbacks> zzalS = new ArrayList();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzalT = new ArrayList();
    private volatile boolean zzalU = false;
    private final AtomicInteger zzalV = new AtomicInteger(0);
    private boolean zzalW = false;
    private final Object zzpV = new Object();

    public zzk(Looper looper, zza zza2) {
        this.zzalQ = zza2;
        this.mHandler = new Handler(looper, (Handler.Callback)this);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleMessage(Message object) {
        if (object.what != 1) {
            Log.wtf((String)"GmsClientEvents", (String)("Don't know how to handle message: " + object.what), (Throwable)new Exception());
            return false;
        }
        GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks)object.obj;
        object = this.zzpV;
        synchronized (object) {
            if (this.zzalU && this.zzalQ.isConnected() && this.zzalR.contains(connectionCallbacks)) {
                connectionCallbacks.onConnected(this.zzalQ.zzoi());
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzx.zzz(connectionCallbacks);
        Object object = this.zzpV;
        synchronized (object) {
            return this.zzalR.contains(connectionCallbacks);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzx.zzz(onConnectionFailedListener);
        Object object = this.zzpV;
        synchronized (object) {
            return this.zzalT.contains(onConnectionFailedListener);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzx.zzz(connectionCallbacks);
        Object object = this.zzpV;
        // MONITORENTER : object
        if (this.zzalR.contains(connectionCallbacks)) {
            Log.w((String)"GmsClientEvents", (String)("registerConnectionCallbacks(): listener " + connectionCallbacks + " is already registered"));
        } else {
            this.zzalR.add(connectionCallbacks);
        }
        // MONITOREXIT : object
        if (!this.zzalQ.isConnected()) return;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, (Object)connectionCallbacks));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzx.zzz(onConnectionFailedListener);
        Object object = this.zzpV;
        synchronized (object) {
            if (this.zzalT.contains(onConnectionFailedListener)) {
                Log.w((String)"GmsClientEvents", (String)("registerConnectionFailedListener(): listener " + onConnectionFailedListener + " is already registered"));
            } else {
                this.zzalT.add(onConnectionFailedListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzx.zzz(connectionCallbacks);
        Object object = this.zzpV;
        synchronized (object) {
            if (!this.zzalR.remove(connectionCallbacks)) {
                Log.w((String)"GmsClientEvents", (String)("unregisterConnectionCallbacks(): listener " + connectionCallbacks + " not found"));
            } else if (this.zzalW) {
                this.zzalS.add(connectionCallbacks);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzx.zzz(onConnectionFailedListener);
        Object object = this.zzpV;
        synchronized (object) {
            if (!this.zzalT.remove(onConnectionFailedListener)) {
                Log.w((String)"GmsClientEvents", (String)("unregisterConnectionFailedListener(): listener " + onConnectionFailedListener + " not found"));
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzbT(int n2) {
        boolean bl2 = false;
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            bl2 = true;
        }
        zzx.zza(bl2, (Object)"onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        Object object = this.zzpV;
        synchronized (object) {
            this.zzalW = true;
            Object object2 = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.zzalR);
            int n3 = this.zzalV.get();
            object2 = ((ArrayList)object2).iterator();
            while (true) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks;
                block8: {
                    block7: {
                        if (!object2.hasNext()) break block7;
                        connectionCallbacks = (GoogleApiClient.ConnectionCallbacks)object2.next();
                        if (this.zzalU && this.zzalV.get() == n3) break block8;
                    }
                    this.zzalS.clear();
                    this.zzalW = false;
                    return;
                }
                if (!this.zzalR.contains(connectionCallbacks)) continue;
                connectionCallbacks.onConnectionSuspended(n2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzk(Bundle bundle) {
        boolean bl2 = true;
        boolean bl3 = Looper.myLooper() == this.mHandler.getLooper();
        zzx.zza(bl3, (Object)"onConnectionSuccess must only be called on the Handler thread");
        Object object = this.zzpV;
        synchronized (object) {
            bl3 = !this.zzalW;
            zzx.zzab(bl3);
            this.mHandler.removeMessages(1);
            this.zzalW = true;
            bl3 = this.zzalS.size() == 0 ? bl2 : false;
            zzx.zzab(bl3);
            Object object2 = new ArrayList<GoogleApiClient.ConnectionCallbacks>(this.zzalR);
            int n2 = this.zzalV.get();
            object2 = ((ArrayList)object2).iterator();
            while (true) {
                GoogleApiClient.ConnectionCallbacks connectionCallbacks;
                block6: {
                    block5: {
                        if (!object2.hasNext()) break block5;
                        connectionCallbacks = (GoogleApiClient.ConnectionCallbacks)object2.next();
                        if (this.zzalU && this.zzalQ.isConnected() && this.zzalV.get() == n2) break block6;
                    }
                    this.zzalS.clear();
                    this.zzalW = false;
                    return;
                }
                if (this.zzalS.contains(connectionCallbacks)) continue;
                connectionCallbacks.onConnected(bundle);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zzk(ConnectionResult connectionResult) {
        boolean bl2 = Looper.myLooper() == this.mHandler.getLooper();
        zzx.zza(bl2, (Object)"onConnectionFailure must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        Object object = this.zzpV;
        synchronized (object) {
            Object object2 = new ArrayList<GoogleApiClient.OnConnectionFailedListener>(this.zzalT);
            int n2 = this.zzalV.get();
            object2 = ((ArrayList)object2).iterator();
            while (object2.hasNext()) {
                GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = (GoogleApiClient.OnConnectionFailedListener)object2.next();
                if (!this.zzalU || this.zzalV.get() != n2) {
                    return;
                }
                if (!this.zzalT.contains(onConnectionFailedListener)) continue;
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
            return;
        }
    }

    public void zzqQ() {
        this.zzalU = false;
        this.zzalV.incrementAndGet();
    }

    public void zzqR() {
        this.zzalU = true;
    }

    public static interface zza {
        public boolean isConnected();

        public Bundle zzoi();
    }
}

