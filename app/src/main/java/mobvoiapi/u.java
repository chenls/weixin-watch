/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.Looper
 *  com.google.android.gms.location.FusedLocationProviderApi
 *  com.google.android.gms.location.LocationServices
 */
package mobvoiapi;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.location.LocationListener;
import com.mobvoi.android.location.LocationRequest;
import mobvoiapi.bp;
import mobvoiapi.z;

public class u
implements com.mobvoi.android.location.FusedLocationProviderApi {
    private FusedLocationProviderApi a = LocationServices.FusedLocationApi;

    @Override
    public Location getLastLocation(MobvoiApiClient object) {
        bp.a("MobvoiApiManager", "FusedLocationApiGoogleImpl#getLastLocation()");
        object = z.a((MobvoiApiClient)object);
        return this.a.getLastLocation((GoogleApiClient)object);
    }

    @Override
    public PendingResult<Status> removeLocationUpdates(MobvoiApiClient object, PendingIntent pendingIntent) {
        bp.a("MobvoiApiManager", "FusedLocationApiGoogleImpl#removeLocationUpdates()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.removeLocationUpdates((GoogleApiClient)object, pendingIntent));
    }

    @Override
    public PendingResult<Status> removeLocationUpdates(MobvoiApiClient object, LocationListener locationListener) {
        bp.a("MobvoiApiManager", "FusedLocationApiGoogleImpl#removeLocationUpdates()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.removeLocationUpdates((GoogleApiClient)object, z.a(locationListener)));
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient object, LocationRequest locationRequest, PendingIntent pendingIntent) {
        bp.a("MobvoiApiManager", "FusedLocationApiGoogleImpl#requestLocationUpdates()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.requestLocationUpdates((GoogleApiClient)object, z.a(locationRequest), pendingIntent));
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient object, LocationRequest locationRequest, LocationListener locationListener) {
        bp.a("MobvoiApiManager", "FusedLocationApiGoogleImpl#requestLocationUpdates()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.requestLocationUpdates((GoogleApiClient)object, z.a(locationRequest), z.a(locationListener)));
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient object, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
        bp.a("MobvoiApiManager", "FusedLocationApiGoogleImpl#requestLocationUpdates()");
        object = z.a((MobvoiApiClient)object);
        return z.a(this.a.requestLocationUpdates((GoogleApiClient)object, z.a(locationRequest), z.a(locationListener), looper));
    }
}

