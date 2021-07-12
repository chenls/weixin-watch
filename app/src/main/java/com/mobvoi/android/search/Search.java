/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.mobvoi.android.search;

import android.content.Context;
import android.os.Looper;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.search.OneboxApi;
import mobvoiapi.av;
import mobvoiapi.aw;

public class Search {
    public static final Api API;
    public static final Api.Key<aw> CLIENT_KEY;
    public static final OneboxApi OneboxApi;
    private static final Api.Builder<aw> a;

    static {
        CLIENT_KEY = new Api.Key("Search.API");
        a = new Api.Builder<aw>(){

            @Override
            public aw build(Context context, Looper looper, MobvoiApiClient.ConnectionCallbacks connectionCallbacks, MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new aw(context, looper, connectionCallbacks, onConnectionFailedListener);
            }

            @Override
            public int getPriority() {
                return 0x7FFFFFFE;
            }
        };
        API = new Api(a, CLIENT_KEY);
        OneboxApi = new av();
    }
}

