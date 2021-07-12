/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 */
package mobvoiapi;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.mobvoi.android.common.ConnectionResult;
import com.mobvoi.android.common.api.Api;
import com.mobvoi.android.common.api.MobvoiApiClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mobvoiapi.ag;
import mobvoiapi.bp;
import mobvoiapi.c;
import mobvoiapi.d;
import mobvoiapi.f;
import mobvoiapi.g;

public abstract class e<T extends IInterface>
implements Api.Connection,
f.a {
    final Handler a;
    private final List<? extends e<?>> b = new ArrayList();
    private boolean c = false;
    private Context d;
    private int e = 1;
    private Looper f;
    private f g;
    private d h = null;
    private T i;

    protected e(Context context, Looper looper, MobvoiApiClient.ConnectionCallbacks connectionCallbacks, MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener, String[] stringArray) {
        this.d = (Context)mobvoiapi.a.a(context);
        mobvoiapi.a.a(onConnectionFailedListener, (Object)"Looper must not be null");
        this.f = looper;
        this.g = new f(this.d, this.f, this);
        this.a = new a(this.f);
        this.a((MobvoiApiClient.ConnectionCallbacks)mobvoiapi.a.a(connectionCallbacks));
        this.a((MobvoiApiClient.OnConnectionFailedListener)mobvoiapi.a.a(onConnectionFailedListener));
    }

    static <I extends IInterface> I a(e<I> e2, I i2) {
        e2.i = i2;
        return i2;
    }

    static d a(e<?> e2, d d2) {
        e2.h = d2;
        return d2;
    }

    static f a(e<?> e2) {
        return e2.g;
    }

    private void a(int n2) {
        bp.a("MmsClient", "status change, from status: " + this.e + ", to status" + n2);
        if (this.e != n2) {
            if (this.e == 3 && n2 == 1) {
                this.j();
            }
            this.e = n2;
            if (n2 == 3) {
                this.i();
            }
        }
    }

    static void a(e<?> e2, int n2) {
        super.a(n2);
    }

    static List<?> b(e<?> e2) {
        return e2.b;
    }

    static <I extends IInterface> I c(e<I> e2) {
        return (I)e2.i;
    }

    static d d(e<?> e2) {
        return e2.h;
    }

    static Context e(e<?> e2) {
        return e2.d;
    }

    protected abstract T a(IBinder var1);

    protected final void a() {
        bp.a("MmsClient", "in ensure connected, state = " + this.isConnected());
        if (!this.isConnected()) {
            throw new IllegalStateException("not connected yet.");
        }
    }

    protected void a(int n2, IBinder iBinder, Bundle bundle) {
        bp.a("MmsClient", "on post init handler, status = " + n2);
        this.a.obtainMessage(1, (Object)new b(n2, iBinder, bundle)).sendToTarget();
    }

    public void a(MobvoiApiClient.ConnectionCallbacks connectionCallbacks) {
        bp.b("MmsClient", "register connection callbacks");
        this.g.a(connectionCallbacks);
    }

    public void a(MobvoiApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        bp.b("MmsClient", "register connection failed listener");
        this.g.a(onConnectionFailedListener);
    }

    protected abstract void a(mobvoiapi.d var1, c var2) throws RemoteException;

    @Override
    public Bundle b() {
        return null;
    }

    protected final void b(IBinder iBinder) {
        try {
            this.a(d.a.a(iBinder), new c(this));
            return;
        }
        catch (RemoteException remoteException) {
            return;
        }
    }

    @Override
    public boolean c() {
        return this.c;
    }

    @Override
    public void connect() {
        this.c = true;
        this.a(2);
        if (this.h != null) {
            bp.d("MmsClient", "discard a connect request, another connect task is still running.");
            this.i = null;
            mobvoiapi.g.a(this.d).b(this.g(), this.h);
        }
        this.h = new d(this);
        if (!mobvoiapi.g.a(this.d).a(this.g(), this.h)) {
            bp.d("MmsClient", "connect to service failed, action : " + this.g());
            this.a.obtainMessage(3, (Object)9).sendToTarget();
        }
    }

    public T d() {
        this.a();
        bp.a("MmsClient", "get service, service: " + this.i);
        return this.i;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        this.c = false;
        List<? extends e<?>> list = this.b;
        synchronized (list) {
            Iterator<e<?>> iterator = this.b.iterator();
            while (iterator.hasNext()) {
                iterator.next().c();
            }
            this.b.clear();
            this.a(1);
            this.i = null;
            if (this.h != null) {
                mobvoiapi.g.a(this.d).b(this.g(), this.h);
                this.h = null;
            }
            return;
        }
    }

    public Context e() {
        return this.d;
    }

    protected abstract String f();

    protected abstract String g();

    @Override
    public Looper getLooper() {
        return this.f;
    }

    public boolean h() {
        return this.e == 2;
    }

    protected void i() {
    }

    @Override
    public boolean isConnected() {
        return this.e == 3;
    }

    protected void j() {
    }

    final class a
    extends Handler {
        private final e<T> b;

        public a(Looper looper) {
            super(looper);
            this.b = e.this;
        }

        public void handleMessage(Message object) {
            bp.a("MmsClient", "msg content: what[" + ((Message)object).what + "], isConnected[" + e.this.isConnected() + "], isConnecting[" + e.this.h() + "].");
            if (((Message)object).what == 1 && !e.this.h() || ((Message)object).what == 2 && !e.this.isConnected()) {
                object = (e)((Message)object).obj;
                ((e)object).a();
                ((e)object).d();
                return;
            }
            if (((Message)object).what == 3) {
                mobvoiapi.e.a(this.b).a(new ConnectionResult((Integer)((Message)object).obj, null));
                return;
            }
            if (((Message)object).what == 4) {
                mobvoiapi.e.a(this.b, 1);
                mobvoiapi.e.a(this.b, null);
                mobvoiapi.e.a(this.b).a((Integer)((Message)object).obj);
                return;
            }
            if (((Message)object).what == 2 || ((Message)object).what == 1) {
                ((e)((Message)object).obj).b();
                return;
            }
            bp.c("MmsClient", "Discard a message, unknown message.");
        }
    }

    public final class b
    extends e<Boolean> {
        final e<T> a;
        public final Bundle b;
        public final IBinder c;
        public final int d;

        public b(int n2, IBinder iBinder, Bundle bundle) {
            super(true);
            this.a = e.this;
            this.d = n2;
            this.c = iBinder;
            this.b = bundle;
        }

        @Override
        protected void a() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected void a(Boolean object) {
            if (object == null) {
                mobvoiapi.e.a(this.a, 1);
                return;
            }
            bp.a("MmsClient", "on excute, status code = " + this.d);
            switch (this.d) {
                default: {
                    mobvoiapi.e.a(this.a, 1);
                    throw new IllegalStateException("occured unknown connect state");
                }
                case 10: {
                    object = this.b != null ? this.b.getParcelable("pendingIntent") : null;
                    object = (PendingIntent)object;
                    if (mobvoiapi.e.d(this.a) != null) {
                        mobvoiapi.g.a(mobvoiapi.e.e(this.a)).b(e.this.g(), mobvoiapi.e.d(this.a));
                        mobvoiapi.e.a(this.a, null);
                    }
                    mobvoiapi.e.a(this.a, 1);
                    mobvoiapi.e.a(this.a, null);
                    mobvoiapi.e.a(this.a).a(new ConnectionResult(this.d, (PendingIntent)object));
                    return;
                }
                case 0: 
            }
            try {
                object = this.c.getInterfaceDescriptor();
                bp.a("MmsClient", "interface descriptor: desc[ " + (String)object + "], local desc[" + this.a.f() + "].");
                if (this.a.f().equals(object)) {
                    mobvoiapi.e.a(this.a, e.this.a(this.c));
                    if (mobvoiapi.e.c(this.a) != null) {
                        mobvoiapi.e.a(this.a, 3);
                        mobvoiapi.e.a(this.a).a();
                        return;
                    }
                }
            }
            catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            mobvoiapi.g.a(mobvoiapi.e.e(this.a)).a(e.this.g(), mobvoiapi.e.d(this.a));
            mobvoiapi.e.a(this.a, null);
            mobvoiapi.e.a(this.a, 1);
            mobvoiapi.e.a(this.a, null);
            mobvoiapi.e.a(this.a).a(new ConnectionResult(8, null));
        }

        @Override
        protected /* synthetic */ void a(Object object) {
            this.b((Boolean)object);
        }

        protected void b(Boolean bl2) {
            this.a(bl2);
        }
    }

    public static final class c
    extends c.a {
        private final e<?> a;

        c(e<?> e2) {
            this.a = e2;
        }

        @Override
        public void a(int n2, IBinder iBinder, Bundle bundle) throws RemoteException {
            mobvoiapi.a.a("onPostInitComplete can be called only once per call to getServiceFromBroker", this.a);
            this.a.a(n2, iBinder, bundle);
        }
    }

    static final class d
    implements ServiceConnection {
        private final e<?> a;

        d(e<?> e2) {
            mobvoiapi.a.a(e2);
            this.a = e2;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bp.a("MmsClient", "on service connected, component name = " + componentName + ", binder = " + iBinder + ".");
            this.a.b(iBinder);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            bp.a("MmsClient", "on service disconnected, component name = " + componentName + ".");
            this.a.a.obtainMessage(4, (Object)1).sendToTarget();
        }
    }

    public abstract class e<L> {
        private boolean a;
        private L b;
        final e<?> f;

        public e(L l2) {
            this.f = e.this;
            this.b = l2;
            this.a = false;
        }

        protected abstract void a();

        protected abstract void a(L var1);

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void b() {
            if (this.a) {
                bp.c("MmsClient", "It is not safe to reuse callback proxy for " + this + ".");
            }
            if (this.b != null) {
                try {
                    this.a(this.b);
                }
                catch (RuntimeException runtimeException) {
                    ag.a("MmsClient", "unexpected exception", runtimeException);
                    this.a();
                }
            } else {
                this.a();
            }
            this.a = true;
            this.d();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void c() {
            L l2 = this.b;
            synchronized (l2) {
                this.b = null;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void d() {
            this.c();
            List<?> list = mobvoiapi.e.b(this.f);
            synchronized (list) {
                mobvoiapi.e.b(this.f).remove(this);
                return;
            }
        }
    }
}

