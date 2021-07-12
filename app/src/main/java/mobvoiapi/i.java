/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 */
package mobvoiapi;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.common.api.Status;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import mobvoiapi.bp;
import mobvoiapi.f;
import mobvoiapi.h;

public class i
implements MobvoiApiClient {
    private final Looper a;
    private final Lock b = new ReentrantLock();
    private final Condition c = this.b.newCondition();
    private final Handler d = new Handler();
    private final Map<Api.Key<?>, Api.Connection> e = new HashMap();
    private int f;
    private ConnectionResult g;
    private volatile int h = 4;
    private final Bundle i = new Bundle();
    private boolean j = false;
    private final f k;
    private final MobvoiApiClient.ConnectionCallbacks l = new MobvoiApiClient.ConnectionCallbacks(){

        @Override
        public void onConnected(Bundle bundle) {
            mobvoiapi.i.b(i.this).lock();
            try {
                bp.a("MobvoiApiClientImpl", "on connected start, api count = " + i.this.f);
                if (mobvoiapi.i.c(i.this) == 1) {
                    if (bundle != null) {
                        mobvoiapi.i.d(i.this).putAll(bundle);
                    }
                    mobvoiapi.i.a(i.this);
                }
                return;
            }
            finally {
                mobvoiapi.i.b(i.this).unlock();
            }
        }

        @Override
        public void onConnectionSuspended(int n2) {
            mobvoiapi.i.b(i.this).lock();
            try {
                bp.a("MobvoiApiClientImpl", "on connection suspended start, api count = " + i.this.f);
                i.this.a(n2);
                return;
            }
            finally {
                mobvoiapi.i.b(i.this).unlock();
            }
        }
    };
    private final MobvoiApiClient.OnConnectionFailedListener m = new MobvoiApiClient.OnConnectionFailedListener(){

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            bp.a("MobvoiApiClientImpl", "on connection failed start, api count = " + i.this.f);
            i.this.k.a(connectionResult);
        }
    };
    private f.a n = new f.a(){

        @Override
        public Bundle b() {
            return null;
        }

        @Override
        public boolean c() {
            return i.this.j;
        }

        @Override
        public boolean isConnected() {
            return i.this.isConnected();
        }
    };

    /*
     * Enabled aggressive block sorting
     */
    public i(Context context, Set<Api> iterator, Set<MobvoiApiClient.ConnectionCallbacks> object, Set<MobvoiApiClient.OnConnectionFailedListener> builder, Handler object2) {
        this.a = object2 != null ? object2.getLooper() : context.getMainLooper();
        this.k = new f(context, this.a, this.n);
        object = object.iterator();
        while (object.hasNext()) {
            MobvoiApiClient.ConnectionCallbacks connectionCallbacks = (MobvoiApiClient.ConnectionCallbacks)object.next();
            this.k.a(connectionCallbacks);
        }
        object = builder.iterator();
        while (object.hasNext()) {
            builder = (MobvoiApiClient.OnConnectionFailedListener)object.next();
            this.k.a((MobvoiApiClient.OnConnectionFailedListener)((Object)builder));
        }
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            object = (Api)iterator.next();
            builder = ((Api)object).getBuilder();
            this.e.put(((Api)object).getKey(), (Api.Connection)mobvoiapi.i.a(builder, context, this.a, this.l, this.m));
        }
        return;
    }

    private static <C extends Api.Connection, O> C a(Api.Builder<C> builder, Context context, Looper looper, MobvoiApiClient.ConnectionCallbacks connectionCallbacks, MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return builder.build(context, looper, connectionCallbacks, onConnectionFailedListener);
    }

    private void a() {
        this.b.lock();
        try {
            this.d.removeMessages(1);
            return;
        }
        finally {
            this.b.unlock();
        }
    }

    private void a(int n2) {
        this.b.lock();
        try {
            ++this.f;
            if (this.f == this.e.size() && this.g == null) {
                this.h = 4;
                this.k.a(n2);
            }
            if (this.h == 4) {
                this.c();
            }
            return;
        }
        finally {
            this.b.unlock();
        }
    }

    public static void a(i i2) {
        i2.b();
    }

    static Lock b(i i2) {
        return i2.b;
    }

    private void b() {
        this.b.lock();
        try {
            --this.f;
            bp.a("MobvoiApiClientImpl", "connect client start, api count = " + this.f);
            if (this.f == 0 && this.g == null) {
                this.h = 2;
                this.k.a(this.i);
            }
            this.c.signalAll();
            return;
        }
        finally {
            this.b.unlock();
        }
    }

    static int c(i i2) {
        return i2.h;
    }

    private void c() {
        this.d.post(new Runnable(){

            @Override
            public void run() {
                if (!i.this.isConnected()) {
                    i.this.d.postDelayed((Runnable)this, 60000L);
                }
                bp.a("MobvoiApiClientImpl", "Start resuming()");
                i.this.connect();
            }
        });
    }

    static Bundle d(i i2) {
        return i2.i;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ConnectionResult blockingConnect() {
        bp.a("MobvoiApiClientImpl", "blocking connect start.");
        boolean bl2 = Looper.myLooper() != Looper.getMainLooper();
        mobvoiapi.a.b(bl2, "blockingConnect can not be called in UI thread.");
        this.b.lock();
        try {
            this.connect();
            while (bl2 = this.isConnecting()) {
                try {
                    this.c.await();
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    ConnectionResult connectionResult = new ConnectionResult(15, null);
                    this.b.unlock();
                    return connectionResult;
                }
            }
            if (this.isConnected()) {
                ConnectionResult connectionResult = ConnectionResult.SUCCESS_CONNECTION_RESULT;
                return connectionResult;
            }
            if (this.g != null) {
                ConnectionResult connectionResult = this.g;
                return connectionResult;
            }
            ConnectionResult connectionResult = new ConnectionResult(13, null);
            return connectionResult;
        }
        finally {
            this.b.unlock();
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public ConnectionResult blockingConnect(long var1_1, TimeUnit var3_2) {
        bp.a("MobvoiApiClientImpl", "blocking connect with timeout start.");
        if (Looper.myLooper() != Looper.getMainLooper()) {
            var6_6 = true;
lbl4:
            // 2 sources

            while (true) {
                mobvoiapi.a.b(var6_6, "blockingConnect can not be called in UI thread.");
                this.b.lock();
                this.connect();
                var1_1 = var3_2.toNanos(var1_1);
                while (var6_6 = this.isConnecting()) {
                    var1_1 = var4_7 = this.c.awaitNanos(var1_1);
                    if (var4_7 > 0L) continue;
                }
                ** break block15
                {
                    var3_2 = new ConnectionResult(14, null);
                    this.b.unlock();
                    return var3_2;
                    break;
                }
                break;
            }
        }
        var6_6 = false;
        ** while (true)
        catch (InterruptedException var3_3) {
            Thread.currentThread().interrupt();
            var3_4 = new ConnectionResult(15, null);
            return var3_4;
        }
lbl-1000:
        // 1 sources

        {
            if (this.isConnected()) {
                var3_2 = ConnectionResult.SUCCESS_CONNECTION_RESULT;
                return var3_2;
            }
            if (this.g != null) {
                var3_2 = this.g;
                return var3_2;
            }
            var3_2 = new ConnectionResult(13, null);
            return var3_2;
        }
        {
            catch (Throwable var3_5) {
                throw var3_5;
            }
        }
        finally {
            this.b.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void connect() {
        bp.a("MobvoiApiClientImpl", "connect start.");
        this.b.lock();
        this.j = true;
        try {
            if (this.isConnected()) return;
            if (this.isConnecting()) return;
            this.f = this.e.size();
            this.h = 1;
            this.g = null;
            Iterator<Api.Connection> iterator = this.e.values().iterator();
            this.i.clear();
            while (iterator.hasNext()) {
                iterator.next().connect();
            }
            this.k.a(this.i);
            return;
        }
        finally {
            this.b.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        bp.a("MobvoiApiClientImpl", "disconnect start.");
        this.j = false;
        this.a();
        this.b.lock();
        try {
            this.h = 3;
            for (Api.Connection connection : this.e.values()) {
                if (!connection.isConnected()) continue;
                connection.disconnect();
            }
            this.h = 4;
            return;
        }
        finally {
            this.b.unlock();
        }
    }

    @Override
    public Looper getLooper() {
        return this.a;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isConnected() {
        this.b.lock();
        try {
            int n2 = this.h;
            boolean bl2 = n2 == 2;
            return bl2;
        }
        finally {
            this.b.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean isConnecting() {
        boolean bl2;
        block3: {
            block2: {
                bl2 = true;
                this.b.lock();
                try {
                    int n2 = this.h;
                    if (n2 != 1) break block2;
                    break block3;
                }
                catch (Throwable throwable) {
                    this.b.unlock();
                    throw throwable;
                }
            }
            bl2 = false;
        }
        this.b.unlock();
        return bl2;
    }

    @Override
    public void reconnect() {
        this.disconnect();
        this.connect();
    }

    @Override
    public void registerConnectionCallbacks(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        bp.a("MobvoiApiClientImpl", "register connection callbacks start.");
        this.k.a(connectionCallbacks);
    }

    @Override
    public void registerConnectionFailedListener(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        bp.a("MobvoiApiClientImpl", "register connection failed listener start.");
        this.k.a(onConnectionFailedListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <A extends Api.Connection, T extends h.b<? extends Result, A>> T setResult(T t2) {
        this.b.lock();
        try {
            bp.a("MobvoiApiClientImpl", "in set result, result = " + t2 + ", isConnected = " + this.isConnected());
            if (this.isConnected()) {
                Api.Key<A> key = t2.c();
                Api.Connection connection = this.e.get(key);
                if (connection == null) {
                    throw new RuntimeException(String.format(Locale.US, "You are invoking APIs from '%s' but didn't request it. Please request the API when build MobvoiApiClient.", key.getApiName()));
                }
                t2.b((Api.Connection)connection);
                return t2;
            }
        }
        finally {
            this.b.unlock();
        }
        t2.a(new h.c(this.getLooper()));
        t2.b(new Status(9));
        return t2;
    }

    @Override
    public void unregisterConnectionCallbacks(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        bp.a("MobvoiApiClientImpl", "unregister connection callbacks start.");
        this.k.b(connectionCallbacks);
    }

    @Override
    public void unregisterConnectionFailedListener(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        bp.a("MobvoiApiClientImpl", "unregister connection failed listener start.");
        this.k.b(onConnectionFailedListener);
    }

    static interface a<A extends Api.Connection> {
    }

    static interface b {
        public void a(a<?> var1);
    }
}

