/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.mobvoi.android.wearable;

import android.content.Context;
import android.os.Looper;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.wearable.DataApi;
import com.mobvoi.android.wearable.MessageApi;
import com.mobvoi.android.wearable.NodeApi;
import mobvoiapi.ac;
import mobvoiapi.ae;
import mobvoiapi.bb;
import mobvoiapi.bf;
import mobvoiapi.bj;
import mobvoiapi.y;

public class Wearable {
    public static final Api API;
    public static final Api.Key<bj> CLIENT_KEY;
    public static final bf ConnectionApi;
    public static final DataApi DataApi;
    public static final MessageApi MessageApi;
    public static final NodeApi NodeApi;
    private static final Api.Builder<bj> a;

    static {
        CLIENT_KEY = new Api.Key("Wearable.API");
        a = new Api.Builder<bj>(){

            @Override
            public bj build(Context context, Looper looper, MobvoiApiClient.ConnectionCallbacks connectionCallbacks, MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new bj(context, looper, connectionCallbacks, onConnectionFailedListener);
            }

            @Override
            public int getPriority() {
                return Integer.MAX_VALUE;
            }
        };
        API = new Api(a, CLIENT_KEY);
        ConnectionApi = new bb();
        DataApi = new y();
        MessageApi = new ac();
        NodeApi = new ae();
    }
}

