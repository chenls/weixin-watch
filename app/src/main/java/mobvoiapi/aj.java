/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.Looper
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.location.FusedLocationProviderApi;
import com.mobvoi.android.location.LocationListener;
import com.mobvoi.android.location.LocationRequest;
import com.mobvoi.android.location.internal.LocationRequestInternal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import mobvoiapi.ao;
import mobvoiapi.ap;

public class aj
implements FusedLocationProviderApi {
    @Override
    public Location getLastLocation(MobvoiApiClient mobvoiApiClient) {
        final Location location = new Location("gps");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        mobvoiApiClient.setResult(new ao<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(ap ap2) throws RemoteException {
                ap2 = ap2.k();
                location.set((Location)ap2);
                countDownLatch.countDown();
            }

            protected Status c(Status status) {
                return status;
            }
        });
        try {
            countDownLatch.await(1000L, TimeUnit.MILLISECONDS);
            return location;
        }
        catch (InterruptedException interruptedException) {
            return location;
        }
    }

    @Override
    public PendingResult<Status> removeLocationUpdates(MobvoiApiClient mobvoiApiClient, final PendingIntent pendingIntent) {
        return mobvoiApiClient.setResult(new ao<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(ap ap2) throws RemoteException {
                ap2.a((ao<Status>)this, pendingIntent);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    @Override
    public PendingResult<Status> removeLocationUpdates(MobvoiApiClient mobvoiApiClient, final LocationListener locationListener) {
        return mobvoiApiClient.setResult(new ao<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(ap ap2) throws RemoteException {
                ap2.a((ao<Status>)this, locationListener);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient mobvoiApiClient, final LocationRequest locationRequest, final PendingIntent pendingIntent) {
        return mobvoiApiClient.setResult(new ao<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(ap ap2) throws RemoteException {
                ap2.a(this, new LocationRequestInternal(locationRequest), pendingIntent);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient mobvoiApiClient, final LocationRequest locationRequest, final LocationListener locationListener) {
        if (Looper.myLooper() == null) {
            throw new NullPointerException("Can't create handler inside thread that has not called Looper.prepare()");
        }
        return mobvoiApiClient.setResult(new ao<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(ap ap2) throws RemoteException {
                ap2.a(this, new LocationRequestInternal(locationRequest), locationListener, null);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }

    @Override
    public PendingResult<Status> requestLocationUpdates(MobvoiApiClient mobvoiApiClient, final LocationRequest locationRequest, final LocationListener locationListener, final Looper looper) {
        return mobvoiApiClient.setResult(new ao<Status>(){

            @Override
            protected /* synthetic */ Result a(Status status) {
                return this.c(status);
            }

            @Override
            protected void a(ap ap2) throws RemoteException {
                ap2.a(this, new LocationRequestInternal(locationRequest), locationListener, looper);
            }

            protected Status c(Status status) {
                return status;
            }
        });
    }
}

