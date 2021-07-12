/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.mobvoi.android.common.api;

import android.content.Context;
import android.os.Looper;
import com.mobvoi.android.common.api.MobvoiApiClient;

public class Api {
    private final Builder<?> a;
    private final Key<?> b;

    public <C extends Connection> Api(Builder<C> builder, Key<C> key) {
        this.a = builder;
        this.b = key;
    }

    public Builder<?> getBuilder() {
        return this.a;
    }

    public Key<?> getKey() {
        return this.b;
    }

    public static interface Builder<T extends Connection> {
        public T build(Context var1, Looper var2, MobvoiApiClient.ConnectionCallbacks var3, MobvoiApiClient.OnConnectionFailedListener var4);

        public int getPriority();
    }

    public static interface Connection {
        public void connect();

        public void disconnect();

        public Looper getLooper();

        public boolean isConnected();
    }

    public static final class Key<C extends Connection> {
        private String a;

        public Key(String string2) {
            this.a = string2;
        }

        public String getApiName() {
            return this.a;
        }
    }
}

