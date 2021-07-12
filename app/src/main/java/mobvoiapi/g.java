/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ResolveInfo
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.IBinder
 *  android.os.Message
 */
package mobvoiapi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import mobvoiapi.ag;
import mobvoiapi.bp;
import mobvoiapi.e;

public class g
implements Handler.Callback {
    private static final Object a = new Object();
    private static g b;
    private final Context c;
    private final Map<String, a> d = new HashMap<String, a>();
    private final Handler e;

    private g(Context context) {
        this.c = context.getApplicationContext();
        this.e = new Handler(context.getMainLooper(), (Handler.Callback)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Intent a(Context object, Intent object2) {
        if ((object = object.getPackageManager().queryIntentServices((Intent)object2, 0)) != null && object.size() == 1) {
            object = (ResolveInfo)object.get(0);
            object = new ComponentName(object.serviceInfo.packageName, object.serviceInfo.name);
            object2 = new Intent((Intent)object2);
            object2.setComponent((ComponentName)object);
            return object2;
        }
        object2 = new StringBuilder().append("failed to get service component: ");
        object = object != null ? Integer.valueOf(object.size()) : "null";
        ag.c("MmsHandleCallback", ((StringBuilder)object2).append(object).toString());
        return null;
    }

    static Map<String, a> a(g g2) {
        return g2.d;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static g a(Context object) {
        Object object2 = a;
        synchronized (object2) {
            if (b != null) return b;
            b = new g((Context)object);
            return b;
        }
    }

    /*
     * Recovered potentially malformed switches.  Disable with '--allowmalformedswitch false'
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean a(String var1_1, e.d var2_2) {
        var5_3 = this.d;
        synchronized (var5_3) {
            var4_4 = this.d.get(var1_1);
            if (var4_4 == null) ** GOTO lbl22
            this.e.removeMessages(0, (Object)var4_4);
            if (var4_4.c(var2_2)) {
                throw new IllegalStateException("Trying to bind a MmsServiceConnection that was already connected before.  startServiceAction=" + (String)var1_1);
            }
            var4_4.a(var2_2);
            bp.a("MmsHandleCallback", "bind service, state: " + var4_4.f());
            switch (var4_4.f()) {
                case 1: {
                    var2_2.onServiceConnected(var4_4.e(), var4_4.d());
                    var1_1 = var4_4;
                    return var1_1.g();
                }
                case 2: {
                    var2_2 = this.a(this.c, new Intent((String)var1_1));
                    var1_1 = var4_4;
                    if (var2_2 == null) return var1_1.g();
                    var4_4.a(this.c.bindService((Intent)var2_2, var4_4.a(), 129));
                    var1_1 = var4_4;
                    return var1_1.g();
                }
lbl22:
                // 1 sources

                bp.a("MmsHandleCallback", "bind service with a new connection: " + (String)var1_1);
                var4_4 = new a((String)var1_1);
                var4_4.a(var2_2);
                var2_2 = this.a(this.c, new Intent((String)var1_1));
                if (var2_2 != null) {
                    var4_4.a(this.c.bindService((Intent)var2_2, var4_4.a(), 129));
                    ag.a("MmsHandleCallback", "bind return state: " + var4_4.g());
                    this.d.put((String)var1_1, var4_4);
                    var1_1 = var4_4;
                    return var1_1.g();
                }
                ag.c("MmsHandleCallback", "cannot bind, failed to get intent");
                var1_1 = var4_4;
                return var1_1.g();
                default: {
                    var1_1 = var4_4;
                }
            }
            return var1_1.g();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(String string2, e.d d2) {
        Map<String, a> map = this.d;
        synchronized (map) {
            bp.a("MmsHandleCallback", "unbind service");
            a a2 = this.d.get(string2);
            if (a2 == null) {
                throw new IllegalStateException("unkonwn service for service action: " + string2);
            }
            if (!a2.c(d2)) {
                throw new IllegalStateException("Trying to unbind a MmsServiceConnection  that was not bound before.  startServiceAction=" + string2);
            }
            a2.b(d2);
            if (a2.c()) {
                string2 = this.e.obtainMessage(0, (Object)a2);
                this.e.sendMessageDelayed((Message)string2, 5000L);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleMessage(Message object) {
        if (object.what != 0) {
            return false;
        }
        a a2 = (a)object.obj;
        object = this.d;
        synchronized (object) {
            if (a2.c()) {
                this.c.unbindService(a2.a());
                this.d.remove(a2.b());
            }
            return true;
        }
    }

    static final class mobvoiapi.g$a {
        private int a;
        private ComponentName b;
        private IBinder c;
        private final Set<e.d> d = new HashSet<e.d>();
        private final a e = new a(this);
        private final String f;
        private boolean g;

        public mobvoiapi.g$a(String string2) {
            this.f = string2;
            this.a = 0;
        }

        static int a(mobvoiapi.g$a a2, int n2) {
            a2.a = n2;
            return n2;
        }

        static ComponentName a(mobvoiapi.g$a a2, ComponentName componentName) {
            a2.b = componentName;
            return componentName;
        }

        static IBinder a(mobvoiapi.g$a a2, IBinder iBinder) {
            a2.c = iBinder;
            return iBinder;
        }

        static Set<e.d> a(mobvoiapi.g$a a2) {
            return a2.d;
        }

        public ServiceConnection a() {
            return this.e;
        }

        public void a(e.d d2) {
            this.d.add(d2);
        }

        public void a(boolean bl2) {
            this.g = bl2;
        }

        public String b() {
            return this.f;
        }

        public void b(e.d d2) {
            this.d.remove(d2);
        }

        public boolean c() {
            return this.d.isEmpty();
        }

        public boolean c(e.d d2) {
            return this.d.contains(d2);
        }

        public IBinder d() {
            return this.c;
        }

        public ComponentName e() {
            return this.b;
        }

        public int f() {
            return this.a;
        }

        public boolean g() {
            return this.g;
        }

        public static class a
        implements ServiceConnection {
            private final mobvoiapi.g$a a;

            public a(mobvoiapi.g$a a2) {
                this.a = a2;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Map<String, mobvoiapi.g$a> map = mobvoiapi.g.a(b);
                synchronized (map) {
                    mobvoiapi.g$a.a(this.a, iBinder);
                    mobvoiapi.g$a.a(this.a, componentName);
                    Iterator<e.d> iterator = mobvoiapi.g$a.a(this.a).iterator();
                    while (true) {
                        if (!iterator.hasNext()) {
                            mobvoiapi.g$a.a(this.a, 1);
                            return;
                        }
                        iterator.next().onServiceConnected(componentName, iBinder);
                    }
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void onServiceDisconnected(ComponentName componentName) {
                Map<String, mobvoiapi.g$a> map = mobvoiapi.g.a(b);
                synchronized (map) {
                    mobvoiapi.g$a.a(this.a, null);
                    mobvoiapi.g$a.a(this.a, componentName);
                    Iterator<e.d> iterator = mobvoiapi.g$a.a(this.a).iterator();
                    while (true) {
                        if (!iterator.hasNext()) {
                            mobvoiapi.g$a.a(this.a, 2);
                            return;
                        }
                        iterator.next().onServiceDisconnected(componentName);
                    }
                }
            }
        }
    }
}

