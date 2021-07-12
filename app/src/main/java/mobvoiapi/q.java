/*
 * Decompiled with CFR 0.151.
 */
package mobvoiapi;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mobvoi.android.common.api.MobvoiApiClient;
import mobvoiapi.bp;
import mobvoiapi.z;

public class q
implements GoogleApiClient.OnConnectionFailedListener {
    private MobvoiApiClient.OnConnectionFailedListener a;

    public q(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.a = onConnectionFailedListener;
    }

    public boolean equals(Object object) {
        if (object instanceof q) {
            return this.a.equals(((q)object).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        bp.a("MobvoiApiManager", "OnConnectionFailedListenerWrapper#onConnectionFailed()");
        this.a.onConnectionFailed(z.a(connectionResult));
    }
}

