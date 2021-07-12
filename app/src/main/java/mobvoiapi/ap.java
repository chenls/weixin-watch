/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.location.Location
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.Status;
import com.mobvoi.android.location.LocationListener;
import com.mobvoi.android.location.internal.LocationRequestInternal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import mobvoiapi.ak;
import mobvoiapi.al;
import mobvoiapi.am;
import mobvoiapi.an;
import mobvoiapi.ao;
import mobvoiapi.aq;
import mobvoiapi.bp;
import mobvoiapi.d;
import mobvoiapi.e;

public class ap
extends e<am> {
    private final Map<LocationListener, aq> b = new HashMap<LocationListener, aq>();

    public ap(Context context, Looper looper, MobvoiApiClient.ConnectionCallbacks connectionCallbacks, MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, new String[0]);
    }

    @Override
    protected /* synthetic */ IInterface a(IBinder iBinder) {
        return this.c(iBinder);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void a(int var1_1, IBinder var2_2, Bundle var3_3) {
        bp.a("LocationServiceAdapter", "on post init handler, statusCode = " + var1_1);
        if (var1_1 != 0) {
            return;
        }
        try {
            bp.a("LocationServiceAdapter", "on post init handler, service = " + var2_2);
            var5_4 = am.a.a(var2_2);
            var4_5 = this.b;
            synchronized (var4_5) {
                var6_7 = this.b.values().iterator();
            }
        }
        catch (RemoteException var4_6) {
            bp.a("LocationServiceAdapter", "on post init handler, error while adding listener = ", var4_6);
lbl13:
            // 2 sources

            while (true) {
                bp.a("LocationServiceAdapter", "on post init handler finished.");
                super.a(var1_1, var2_2, var3_3);
                return;
            }
        }
        {
            while (var6_7.hasNext()) {
                var7_8 = var6_7.next();
                bp.a("LocationServiceAdapter", "on post init handler, adding Location listener = " + var7_8);
                var5_4.a((ak)new an(){

                    @Override
                    public void a(Status status) throws RemoteException {
                    }
                }, var7_8.a(), var7_8);
            }
        }
        ** while (true)
    }

    @Override
    public void a(final ao<Status> ao2, PendingIntent pendingIntent) throws RemoteException {
        ((am)this.d()).a((ak)new an(){

            @Override
            public void a(Status status) throws RemoteException {
                ao2.a(status);
            }
        }, pendingIntent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(final ao<Status> ao2, LocationListener object) throws RemoteException {
        Map<LocationListener, aq> map = this.b;
        synchronized (map) {
            object = this.b.remove(object);
            if (object == null) {
                ao2.a(new Status(4002));
            } else {
                ((am)this.d()).a((ak)new an(){

                    @Override
                    public void a(Status status) throws RemoteException {
                        ao2.a(status);
                    }
                }, (al)object);
            }
            return;
        }
    }

    public void a(final ao<Status> ao2, LocationRequestInternal locationRequestInternal, PendingIntent pendingIntent) throws RemoteException {
        ((am)this.d()).a((ak)new an(){

            @Override
            public void a(Status status) throws RemoteException {
                ao2.a(status);
            }
        }, locationRequestInternal, pendingIntent);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(final ao<Status> ao2, LocationRequestInternal locationRequestInternal, LocationListener locationListener, Looper object) throws RemoteException {
        bp.a("LocationServiceAdapter", "add location listener start. listener = " + locationListener + ".");
        Map<LocationListener, aq> map = this.b;
        synchronized (map) {
            if (this.b.get(locationListener) != null) {
                ao2.a(new Status(4002));
                bp.a("LocationServiceAdapter", "add location listener 4002 error!");
            } else {
                object = new aq(locationListener, locationRequestInternal, (Looper)object);
                this.b.put(locationListener, (aq)object);
                ((am)this.d()).a((ak)new an(){

                    @Override
                    public void a(Status status) throws RemoteException {
                        ao2.a(status);
                    }
                }, locationRequestInternal, (al)object);
                bp.a("LocationServiceAdapter", "add location listener " + object + " added");
            }
            return;
        }
    }

    @Override
    protected void a(d d2, e.c c2) throws RemoteException {
        d2.b(c2, 0, this.e().getPackageName());
    }

    protected am c(IBinder iBinder) {
        return am.a.a(iBinder);
    }

    @Override
    protected String f() {
        return "com.mobvoi.android.location.internal.ILocationService";
    }

    @Override
    protected String g() {
        return "com.mobvoi.android.location.BIND";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void j() {
        Map<LocationListener, aq> map = this.b;
        synchronized (map) {
            Iterator<LocationListener> iterator = this.b.keySet().iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    this.b.clear();
                    return;
                }
                LocationListener locationListener = iterator.next();
                try {
                    ((am)this.d()).a((ak)new an(){

                        @Override
                        public void a(Status status) throws RemoteException {
                        }
                    }, this.b.get(locationListener));
                }
                catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                    continue;
                }
                break;
            }
        }
    }

    public Location k() throws RemoteException {
        return ((am)this.d()).a();
    }
}

