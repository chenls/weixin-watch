/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  com.google.android.gms.location.LocationListener
 */
package mobvoiapi;

import android.location.Location;
import com.google.android.gms.location.LocationListener;
import mobvoiapi.bp;

public class n
implements LocationListener {
    private com.mobvoi.android.location.LocationListener a;

    public n(com.mobvoi.android.location.LocationListener locationListener) {
        this.a = locationListener;
    }

    public boolean equals(Object object) {
        if (object instanceof n) {
            return this.a.equals(((n)object).a);
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public void onLocationChanged(Location location) {
        bp.a("MobvoiApiManager", "LocationListenerWrapper#onLocationChanged()");
        this.a.onLocationChanged(location);
    }
}

