/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package mobvoiapi;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobvoi.android.common.api.MobvoiApiClient;
import mobvoiapi.bp;

public class k
implements GoogleApiClient.ConnectionCallbacks {
    private MobvoiApiClient.ConnectionCallbacks a;

    public k(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        this.a = connectionCallbacks;
    }

    public boolean equals(Object object) {
        if (object instanceof k) {
            return this.a.equals(((k)object).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override
    public void onConnected(Bundle bundle) {
        bp.a("MobvoiApiManager", "ConnectionCallbacksWrapper#onConnected()");
        this.a.onConnected(bundle);
    }

    @Override
    public void onConnectionSuspended(int n2) {
        bp.a("MobvoiApiManager", "ConnectionCallbacksWrapper#onConnectionSuspended()");
        this.a.onConnectionSuspended(n2);
    }
}

