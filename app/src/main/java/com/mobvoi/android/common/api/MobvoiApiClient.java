/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 */
package com.mobvoi.android.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.Result;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import mobvoiapi.a;
import mobvoiapi.ad;
import mobvoiapi.h;

public interface MobvoiApiClient {
    public ConnectionResult blockingConnect();

    public ConnectionResult blockingConnect(long var1, TimeUnit var3);

    public void connect();

    public void disconnect();

    public Looper getLooper();

    public boolean isConnected();

    public boolean isConnecting();

    public void reconnect();

    public void registerConnectionCallbacks(ConnectionCallbacks var1);

    public void registerConnectionFailedListener(OnConnectionFailedListener var1);

    public <A extends Api.Connection, T extends h.b<? extends Result, A>> T setResult(T var1);

    public void unregisterConnectionCallbacks(ConnectionCallbacks var1);

    public void unregisterConnectionFailedListener(OnConnectionFailedListener var1);

    public static final class Builder {
        private Context a;
        private Handler b;
        private final Set<ConnectionCallbacks> c = new HashSet<ConnectionCallbacks>();
        private final Set<OnConnectionFailedListener> d = new HashSet<OnConnectionFailedListener>();
        private final Set<Api> e = new HashSet<Api>();

        public Builder(Context context) {
            this.a = context;
        }

        public Builder addApi(Api api) {
            this.e.add(api);
            return this;
        }

        public Builder addConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
            this.c.add(connectionCallbacks);
            return this;
        }

        public Builder addOnConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {
            this.d.add(onConnectionFailedListener);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public MobvoiApiClient build() {
            boolean bl2 = !this.e.isEmpty();
            mobvoiapi.a.a(bl2, (Object)"must call addApi() to add at least one API");
            return new ad(this.a, this.e, this.c, this.d, this.b);
        }

        public Builder setHandler(Handler handler) {
            this.b = handler;
            return this;
        }
    }

    public static interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        public void onConnected(Bundle var1);

        public void onConnectionSuspended(int var1);
    }

    public static interface OnConnectionFailedListener {
        public void onConnectionFailed(ConnectionResult var1);
    }
}

