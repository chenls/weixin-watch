/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package mobvoiapi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.MobvoiApiClient;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import mobvoiapi.bp;

public final class f {
    private boolean a = false;
    private final a b;
    private final Handler c;
    private Set<MobvoiApiClient.ConnectionCallbacks> d = new HashSet<MobvoiApiClient.ConnectionCallbacks>();
    private Set<MobvoiApiClient.OnConnectionFailedListener> e = new HashSet<MobvoiApiClient.OnConnectionFailedListener>();

    public f(Context context, Looper looper, a a2) {
        this.c = new b(looper);
        this.b = a2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void a() {
        Set<MobvoiApiClient.ConnectionCallbacks> set = this.d;
        synchronized (set) {
            this.a(this.b.b());
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(int n2) {
        this.c.removeMessages(1);
        Set<MobvoiApiClient.ConnectionCallbacks> set = this.d;
        synchronized (set) {
            bp.b("MmsClientEvents", "suspend");
            this.a = true;
            Iterator<MobvoiApiClient.ConnectionCallbacks> iterator = this.d.iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    this.a = false;
                    return;
                }
                MobvoiApiClient.ConnectionCallbacks connectionCallbacks = iterator.next();
                if (!this.b.c()) continue;
                connectionCallbacks.onConnectionSuspended(n2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(Bundle bundle) {
        boolean bl2 = true;
        Set<MobvoiApiClient.ConnectionCallbacks> set = this.d;
        synchronized (set) {
            bp.b("MmsClientEvents", "on connected.");
            if (this.a) {
                bl2 = false;
            }
            mobvoiapi.a.a(bl2);
            this.c.removeMessages(1);
            this.a = true;
            Iterator<MobvoiApiClient.ConnectionCallbacks> iterator = this.d.iterator();
            while (true) {
                MobvoiApiClient.ConnectionCallbacks connectionCallbacks;
                block8: {
                    block7: {
                        if (!iterator.hasNext()) break block7;
                        connectionCallbacks = iterator.next();
                        bp.a("MmsClientEvents", "call connection callback, inConnect = " + this.b.c() + ", isConnected = " + this.b.isConnected());
                        if (this.b.c() && this.b.isConnected()) break block8;
                    }
                    this.a = false;
                    return;
                }
                connectionCallbacks.onConnected(bundle);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(ConnectionResult connectionResult) {
        this.c.removeMessages(1);
        Set<MobvoiApiClient.OnConnectionFailedListener> set = this.e;
        synchronized (set) {
            bp.b("MmsClientEvents", "connect failed.");
            Iterator<MobvoiApiClient.OnConnectionFailedListener> iterator = this.e.iterator();
            while (true) {
                MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener;
                block6: {
                    block5: {
                        if (!iterator.hasNext()) break block5;
                        onConnectionFailedListener = iterator.next();
                        if (!this.b.c()) break block6;
                    }
                    return;
                }
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        mobvoiapi.a.a(connectionCallbacks);
        bp.b("MmsClientEvents", "register connection callbacks");
        Set<MobvoiApiClient.ConnectionCallbacks> set = this.d;
        synchronized (set) {
            if (this.d.contains(connectionCallbacks)) {
                bp.c("MmsClientEvents", "duplicated registered listener : " + connectionCallbacks + ".");
                if (this.b.isConnected()) {
                    this.c.obtainMessage(1, (Object)connectionCallbacks).sendToTarget();
                }
            } else {
                this.d.add(connectionCallbacks);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void a(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        mobvoiapi.a.a(onConnectionFailedListener);
        bp.b("MmsClientEvents", "register connection failed listener");
        Set<MobvoiApiClient.OnConnectionFailedListener> set = this.e;
        synchronized (set) {
            if (this.e.contains(onConnectionFailedListener)) {
                bp.c("MmsClientEvents", "duplicated register connection failed listener : " + onConnectionFailedListener + ".");
            } else {
                this.e.add(onConnectionFailedListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        mobvoiapi.a.a(connectionCallbacks);
        bp.b("MmsClientEvents", "unregister connection callbacks");
        Set<MobvoiApiClient.ConnectionCallbacks> set = this.d;
        synchronized (set) {
            if (this.d.contains(connectionCallbacks)) {
                this.d.remove(connectionCallbacks);
            } else {
                Log.w((String)"MmsClientEvents", (String)"unregister connection callback failed, no connection callback is found.");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        mobvoiapi.a.a(onConnectionFailedListener);
        bp.b("MmsClientEvents", "unregister connection failed listener");
        Set<MobvoiApiClient.OnConnectionFailedListener> set = this.e;
        synchronized (set) {
            if (this.e.contains(onConnectionFailedListener)) {
                this.e.remove(onConnectionFailedListener);
            } else {
                Log.w((String)"MmsClientEvents", (String)"unregister connection failed listener failed, no unregister connection callback failed, no connection callback is found.  is found.");
            }
            return;
        }
    }

    public static interface a {
        public Bundle b();

        public boolean c();

        public boolean isConnected();
    }

    final class b
    extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void handleMessage(Message message) {
            bp.b("MmsClientEvents", "handle message: what = " + message.what);
            if (message.what != 1) {
                bp.c("MmsClientEvents", "discard an unkonwn message.");
                return;
            }
            Set set = f.this.d;
            synchronized (set) {
                if (f.this.b.c() && f.this.b.isConnected() && f.this.d.contains(message.obj)) {
                    ((MobvoiApiClient.ConnectionCallbacks)message.obj).onConnected(f.this.b.b());
                }
                return;
            }
        }
    }
}

