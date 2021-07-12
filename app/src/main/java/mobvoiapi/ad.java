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
import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.MobvoiApiManager;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.Result;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import mobvoiapi.bp;
import mobvoiapi.h;
import mobvoiapi.i;
import mobvoiapi.s;

public class ad
implements MobvoiApiClient {
    private MobvoiApiClient a;

    public ad(Context context, Set<Api> set, Set<MobvoiApiClient.ConnectionCallbacks> set2, Set<MobvoiApiClient.OnConnectionFailedListener> set3, Handler handler) {
        if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.MMS) {
            this.a = new i(context, set, set2, set3, handler);
            return;
        }
        if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.GMS) {
            this.a = new s(context, set, set2, set3, handler);
            return;
        }
        bp.c("MobvoiApiManager", "create MobvoiApiClientProxy failed, invalid ApiGroup : " + (Object)((Object)MobvoiApiManager.getInstance().getGroup()));
    }

    public MobvoiApiClient a() {
        return this.a;
    }

    @Override
    public ConnectionResult blockingConnect() {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#blockingConnect()");
        return this.a.blockingConnect();
    }

    @Override
    public ConnectionResult blockingConnect(long l2, TimeUnit timeUnit) {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#blockingConnect()");
        return this.a.blockingConnect(l2, timeUnit);
    }

    @Override
    public void connect() {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#connect()");
        this.a.connect();
    }

    @Override
    public void disconnect() {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#disconnect()");
        this.a.disconnect();
    }

    @Override
    public Looper getLooper() {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#getLooper()");
        return this.a.getLooper();
    }

    @Override
    public boolean isConnected() {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#isConnected()");
        return this.a.isConnected();
    }

    @Override
    public boolean isConnecting() {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#isConnecting()");
        return this.a.isConnecting();
    }

    @Override
    public void reconnect() {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#reconnect()");
        this.a.reconnect();
    }

    @Override
    public void registerConnectionCallbacks(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#registerConnectionCallbacks()");
        this.a.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void registerConnectionFailedListener(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#registerConnectionFailedListener()");
        this.a.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override
    public <A extends Api.Connection, T extends h.b<? extends Result, A>> T setResult(T t2) {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#setResult()");
        return this.a.setResult(t2);
    }

    @Override
    public void unregisterConnectionCallbacks(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#unregisterConnectionCallbacks()");
        this.a.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Override
    public void unregisterConnectionFailedListener(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        bp.a("MobvoiApiManager", "MobvoiApiClientProxy#unregisterConnectionFailedListener()");
        this.a.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
}

