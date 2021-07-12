/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.Looper
 */
package mobvoiapi;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import com.mobvoi.android.common.MobvoiApiManager;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.location.FusedLocationProviderApi;
import com.mobvoi.android.location.LocationListener;
import com.mobvoi.android.location.LocationRequest;
import mobvoiapi.ab;
import mobvoiapi.aj;
import mobvoiapi.bp;
import mobvoiapi.u;

public class aa
implements FusedLocationProviderApi,
ab {
    private FusedLocationProviderApi a;

    public aa() {
        MobvoiApiManager.getInstance().registerProxy(this);
        this.a();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a() {
        if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.MMS) {
            this.a = new aj();
        } else if (MobvoiApiManager.getInstance().getGroup() == MobvoiApiManager.ApiGroup.GMS) {
            this.a = new u();
        }
        bp.a("MobvoiApiManager", "load location api success.");
    }

    @Override
    public Location getLastLocation(MobvoiApiClient mobvoiApiClient) {
        bp.a("MobvoiApiManager", "FusedLocationApiProxy#getLastLocation()");
        return this.a.getLastLocation(mobvoiApiClient);
    }

    @Override
    public PendingResult<Status> removeLocationUpdates(MobvoiApiClient mobvoiApiClient, PendingIntent pendingIntent) {
        bp.a("MobvoiApiManager", "FusedLocationApiProxy#removeLocationUpdates()");
        return this.a.removeLocationUpdates(mobvoiApiClient, pendingIntent);
    }

    @Override
    public PendingResult<Status> removeLocationUpdates(MobvoiApiClient mobvoiApiClient, LocationListener locationListener) {
        bp.a("MobvoiApiManager", "FusedLocationApiProxy#removeLocationUpdates()");
        return this.a.removeLocationUpdates(mobvoiApiClient, locationListener);
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient mobvoiApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
        bp.a("MobvoiApiManager", "FusedLocationApiProxy#requestLocationUpdates()");
        return this.a.requestLocationUpdates(mobvoiApiClient, locationRequest, pendingIntent);
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient mobvoiApiClient, LocationRequest locationRequest, LocationListener locationListener) {
        bp.a("MobvoiApiManager", "FusedLocationApiProxy#requestLocationUpdates()");
        return this.a.requestLocationUpdates(mobvoiApiClient, locationRequest, locationListener);
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient mobvoiApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        bp.a("MobvoiApiManager", "FusedLocationApiProxy#requestLocationUpdates()");
        return this.a.requestLocationUpdates(mobvoiApiClient, locationRequest, locationListener, looper);
    }
}

