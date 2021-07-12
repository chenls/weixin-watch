/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.mobvoi.android.location;

import android.content.Context;
import android.os.Looper;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.location.FusedLocationProviderApi;
import mobvoiapi.aa;
import mobvoiapi.ap;
import mobvoiapi.ar;
import mobvoiapi.as;

public class LocationServices {
    public static final Api API;
    public static final Api.Key<ap> CLIENT_KEY;
    public static final FusedLocationProviderApi FusedLocationApi;
    public static final ar SetLocationApi;
    private static final Api.Builder<ap> a;

    static {
        CLIENT_KEY = new Api.Key("LocationServices.API");
        a = new Api.Builder<ap>(){

            @Override
            public ap build(Context context, Looper looper, MobvoiApiClient.ConnectionCallbacks connectionCallbacks, MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
                return new ap(context, looper, connectionCallbacks, onConnectionFailedListener);
            }

            @Override
            public int getPriority() {
                return 0x7FFFFFFD;
            }
        };
        API = new Api(a, CLIENT_KEY);
        FusedLocationApi = new aa();
        SetLocationApi = new as();
    }
}

