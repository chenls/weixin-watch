/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 */
package mobvoiapi;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.UnsupportedException;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.Result;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import mobvoiapi.bp;
import mobvoiapi.h;
import mobvoiapi.z;

public class s
implements MobvoiApiClient {
    private GoogleApiClient a;

    public s(Context object, Set<com.mobvoi.android.common.api.Api> iterator, Set<MobvoiApiClient.ConnectionCallbacks> object2, Set<MobvoiApiClient.OnConnectionFailedListener> set, Handler handler) {
        object = new GoogleApiClient.Builder((Context)object);
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            Object R1 = z.a((com.mobvoi.android.common.api.Api)iterator.next());
            if (R1 == null) continue;
            ((GoogleApiClient.Builder)object).addApi((Api<? extends Api.ApiOptions.NotRequiredOptions>)R1);
        }
        iterator = object2.iterator();
        while (iterator.hasNext()) {
            object2 = z.a(iterator.next());
            if (object2 == null) continue;
            ((GoogleApiClient.Builder)object).addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)object2);
        }
        iterator = set.iterator();
        while (iterator.hasNext()) {
            object2 = z.a((MobvoiApiClient.OnConnectionFailedListener)iterator.next());
            if (object2 == null) continue;
            ((GoogleApiClient.Builder)object).addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener)object2);
        }
        if (handler != null) {
            ((GoogleApiClient.Builder)object).setHandler(handler);
        }
        this.a = ((GoogleApiClient.Builder)object).build();
    }

    public GoogleApiClient a() {
        return this.a;
    }

    @Override
    public ConnectionResult blockingConnect() {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#blockingConnect()");
        return z.a(this.a.blockingConnect());
    }

    @Override
    public ConnectionResult blockingConnect(long l2, TimeUnit timeUnit) {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#blockingConnect()");
        return z.a(this.a.blockingConnect(l2, timeUnit));
    }

    @Override
    public void connect() {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#connect()");
        this.a.connect();
    }

    @Override
    public void disconnect() {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#disconnect()");
        this.a.disconnect();
    }

    @Override
    public Looper getLooper() {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#getLooper()");
        return this.a.getLooper();
    }

    @Override
    public boolean isConnected() {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#isConnected()");
        return this.a.isConnected();
    }

    @Override
    public boolean isConnecting() {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#isConnecting()");
        return this.a.isConnecting();
    }

    @Override
    public void reconnect() {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#reconnect()");
        this.a.reconnect();
    }

    @Override
    public void registerConnectionCallbacks(MobvoiApiClient.ConnectionCallbacks object) {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#registerConnectionCallbacks()");
        object = z.a((MobvoiApiClient.ConnectionCallbacks)object);
        if (object != null) {
            this.a.registerConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)object);
        }
    }

    @Override
    public void registerConnectionFailedListener(MobvoiApiClient.OnConnectionFailedListener object) {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#unregisterConnectionCallbacks()");
        object = z.a((MobvoiApiClient.OnConnectionFailedListener)object);
        if (object != null) {
            this.a.registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener)object);
        }
    }

    @Override
    public <A extends Api.Connection, T extends h.b<? extends Result, A>> T setResult(T t2) {
        throw new UnsupportedException("Can not use ApiClientGoogleImpl#setResult, use #getImplement to get GoogleApi instance.");
    }

    @Override
    public void unregisterConnectionCallbacks(MobvoiApiClient.ConnectionCallbacks object) {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#unregisterConnectionCallbacks()");
        object = z.a((MobvoiApiClient.ConnectionCallbacks)object);
        if (object != null) {
            this.a.unregisterConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)object);
        }
    }

    @Override
    public void unregisterConnectionFailedListener(MobvoiApiClient.OnConnectionFailedListener object) {
        bp.a("MobvoiApiManager", "ApiClientGoogleImpl#unregisterConnectionFailedListener()");
        object = z.a((MobvoiApiClient.OnConnectionFailedListener)object);
        if (object != null) {
            this.a.unregisterConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener)object);
        }
    }
}

