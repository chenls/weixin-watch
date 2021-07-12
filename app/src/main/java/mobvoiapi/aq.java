/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.util.Log
 */
package mobvoiapi;

import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.mobvoi.android.location.LocationListener;
import com.mobvoi.android.location.internal.LocationRequestInternal;
import mobvoiapi.al;
import mobvoiapi.bp;

public class aq
extends al.a {
    private final LocationListener a;
    private final LocationRequestInternal b;
    private final Handler c;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public aq(LocationListener object, LocationRequestInternal locationRequestInternal, Looper looper) {
        void var1_3;
        void var3_6;
        void var2_5;
        this.a = object;
        this.b = var2_5;
        if (var3_6 == null) {
            a a2 = new a();
        } else {
            a a3 = new a((Looper)var3_6);
        }
        this.c = var1_3;
    }

    public LocationRequestInternal a() {
        return this.b;
    }

    @Override
    public void a(Location location) throws RemoteException {
        bp.a("LocationServiceListener", "onLocationChange: " + location);
        Message message = this.c.obtainMessage();
        message.obj = location;
        message.what = 1;
        message.sendToTarget();
    }

    class a
    extends Handler {
        public a() {
        }

        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    Log.e((String)"LocationClientHelper", (String)"unknown message in LocationHandler.handleMessage");
                    return;
                }
                case 1: 
            }
            message = new Location((Location)message.obj);
            aq.this.a.onLocationChanged((Location)message);
        }
    }
}

